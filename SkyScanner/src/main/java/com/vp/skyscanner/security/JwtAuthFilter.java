package com.vp.skyscanner.security;

import com.vp.skyscanner.models.AuthToken;
import com.vp.skyscanner.repositories.AuthTokenRepository;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  private AuthTokenRepository authTokenRepository;


  public JwtAuthFilter() {

  }
  @Autowired
  public void setAuthTokenRepository(AuthTokenRepository authTokenRepository) {
    this.authTokenRepository = authTokenRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.substring(7);
    String username = jwtService.extractUsername(jwt);
    UserDetails userDetails;

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      Optional<AuthToken> authTokenEntity = authTokenRepository.findByTokenValue(jwt);

      if (authTokenEntity.isPresent()) {
        userDetails = customUserDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(jwt, userDetails) && authTokenEntity.get().isValid()) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
    }
  }
}
