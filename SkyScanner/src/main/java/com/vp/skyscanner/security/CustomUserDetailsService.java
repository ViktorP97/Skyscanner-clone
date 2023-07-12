package com.vp.skyscanner.security;

import com.vp.skyscanner.models.UserEntity;
import com.vp.skyscanner.repositories.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(
        Collections.singletonList(user.getRoleType())));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleType> roles) {
    return roles
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());
  }

}