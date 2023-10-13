package com.vp.skyscanner.services;

import com.vp.skyscanner.dtos.AuthDto;
import com.vp.skyscanner.dtos.LoginDto;
import com.vp.skyscanner.dtos.RegisterDto;
import com.vp.skyscanner.exceptions.ObjectNotFoundException;
import com.vp.skyscanner.exceptions.SignatureException;
import com.vp.skyscanner.models.AuthToken;
import com.vp.skyscanner.models.ConfirmationToken;
import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.AuthTokenRepository;
import com.vp.skyscanner.repositories.UserRepository;
import com.vp.skyscanner.security.JwtService;
import com.vp.skyscanner.security.RoleType;
import com.vp.skyscanner.security.SecurityConstants;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;
  private final String emailVerificationLink;
  private final Boolean verifyEmail;
  private final Boolean sendEmail;
  private final UserService userService;
  private final ConfirmationTokenService confirmationTokenService;
  private final AuthTokenRepository authTokenRepository;

  @Autowired
  public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager,
      EmailService emailService,
      @Value("${EMAIL_VERIFICATION_SWITCH}") Boolean verifyEmail,
      @Value("${EMAIL_VERIFICATION_LINK}") String emailVerificationLink,
      @Value("${EMAIL_SEND_SWITCH}") Boolean sendEmail,
      UserService userService, ConfirmationTokenService confirmationTokenService,
      AuthTokenRepository authTokenRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.emailService = emailService;
    this.emailVerificationLink = emailVerificationLink;
    this.verifyEmail = verifyEmail;
    this.sendEmail = sendEmail;
    this.userService = userService;
    this.confirmationTokenService = confirmationTokenService;
    this.authTokenRepository = authTokenRepository;
  }

  @Override
  public String register(RegisterDto registerDto) {

    if (userService.usernameExists(registerDto.getUsername())) {
      return "Username already taken";
    }
    if (userService.emailExists(registerDto.getEmail())) {
      return "Email already in use";
    }
    if (!containDigit(registerDto.getPassword())) {
      return "Password must contain at least one digit";
    }
    if (!containBigLetter(registerDto.getPassword())) {
      return "Password must contain at least one big letter";
    }
    if (!passwordIsLongEnough(registerDto.getPassword())) {
      return "Password must be at least 10 characters long";
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setRoleType(RoleType.USER);
    user.setVerified(false);
    userRepository.save(user);

    ConfirmationToken confirmationToken = new ConfirmationToken();
    confirmationToken.setToken(UUID.randomUUID().toString());
    confirmationToken.setPlayer(user);
    confirmationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
    confirmationTokenService.saveConfirmationToken(confirmationToken);

    String token = confirmationToken.getToken();
    String confirmationLink = emailVerificationLink + token;

    if (!verifyEmail) {
      confirmRegistration(confirmationToken.getToken());
    }

    if (sendEmail) {
      emailService.sendEmail(user.getEmail(),
          buildEmail(user.getUsername(), confirmationLink), "Confirm your email");
    }

    return token;
  }

  @Override
  public UserEntity confirmRegistration(String token) {
    ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
    if (confirmationToken == null) {
      throw new ObjectNotFoundException("Invalid token");
    }

    UserEntity user = confirmationToken.getUser();
    user.verifyUser();
    userService.addUser(user);

    confirmationTokenService.deleteConfirmationToken(confirmationToken);

    return user;
  }

  @Override
  public AuthDto login(LoginDto loginDto) {
    UserEntity user = userRepository.findByUsername(loginDto.getUsername())
        .orElseThrow(() -> new SignatureException("Invalid username or password"));

    if (!user.isVerified()) {
      throw new BadCredentialsException("Account has not been verified");
    }

    if (user.isLogged()) {
      throw new BadCredentialsException("You are already logged in");
    }

    user.setLogged(true);
    userRepository.save(user);
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

    String jwtToken = jwtService.generateToken(user);

    AuthToken authToken = new AuthToken();
    authToken.setTokenValue(jwtToken);
    authToken.setUser(user);
    authToken.setValid(true);
    authToken.setExpirationDate(SecurityConstants.JWT_EXPIRATION);
    authTokenRepository.save(authToken);

    AuthDto authDto = new AuthDto();
    authDto.setToken(jwtToken);

    return authDto;
  }

  @Override
  public String logout(UserEntity user) {
    List<AuthToken> tokenList = authTokenRepository.findAll();
    long id = 0;
    for (AuthToken token : tokenList) {
      if (token.isValid()) {
        id = token.getId();
      }
    }
    Optional<AuthToken> authToken = authTokenRepository.findByUserAndId(user, id);
    if (authToken.isPresent()) {
      authToken.get().setValid(false);
      user.setLogged(false);
      authTokenRepository.save(authToken.get());
      userRepository.save(user);
    }
    return "Logged out successfully.";
  }

  public boolean containDigit(String password) {
    for (Character character : password.toCharArray()) {
      if (Character.isDigit(character)) {
        return true;
      }
    }
    return false;
  }

  public boolean containBigLetter(String password) {
    for (Character character : password.toCharArray()) {
      if (Character.isUpperCase(character)) {
        return true;
      }
    }
    return false;
  }

  public boolean passwordIsLongEnough(String password) {
    return password.length() >= 10;
  }

  private String buildEmail(String username, String confirmationLink) {
    return
        "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n\n"
            + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n\n"
            + "  <table role=\"presentation\" width=\"100%\""
            + " style=\"border-collapse:collapse;min-width:100%;width:100%!important\""
            + " cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
            + "    <tbody><tr>\n"
            + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n\n"
            + "        <table role=\"presentation\" width=\"100%\""
            + " style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\""
            + " cellspacing=\"0\" border=\"0\" align=\"center\">\n"
            + "          <tbody><tr>\n"
            + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
            + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\""
            + " border=\"0\" style=\"border-collapse:collapse\">\n"
            + "                  <tbody><tr>\n"
            + "<td style=\"padding-left:10px\">\n\n"
            + "                    </td>\n"
            + "<td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
            + "                      <span"
            + " style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm"
            + " your email</span>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody></table>\n"
            + "</a>\n"
            + "</td>\n"
            + "</tr>\n"
            + "</tbody></table>\n\n"
            + "</td>\n"
            + "</tr>\n"
            + "  </tbody></table>\n"
            + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\""
            + " align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\""
            + " style=\"border-collapse:collapse;max-width:580px;width:100%!important\""
            + " width=\"100%\">\n"
            + "    <tbody><tr>\n"
            + "<td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
            + "      <td>\n\n"
            + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\""
            + " border=\"0\" style=\"border-collapse:collapse\">\n"
            + "                  <tbody><tr>\n"
            + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
            + "                  </tr>\n"
            + "</tbody></table>\n\n"
            + "</td>\n"
            + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n"
            + "    </tr>\n"
            + "  </tbody></table>\n\n\n\n"
            + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\""
            + " align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\""
            + " style=\"border-collapse:collapse;max-width:580px;width:100%!important\""
            + " width=\"100%\">\n"
            + "    <tbody><tr>\n"
            + "      <td height=\"30\"><br></td>\n"
            + "    </tr>\n"
            + "    <tr>\n"
            + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
            + "      <td"
            + " style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n\n"
            + "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
            + username
            + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank"
            + " you for registering. Please click on the below confirmationLink to activate your"
            + " account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid"
            + " #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0"
            + " 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
            + confirmationLink
            + "\">Activate Now</a> </p></blockquote>\n"
            + " Link will expire in 15 minutes. <p>See you soon</p>\n</td>\n"
            + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
            + "    </tr>\n<tr>\n<td height=\"30\"><br></td>\n</tr>\n"
            + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n\n"
            + "</div></div>";
  }
}
