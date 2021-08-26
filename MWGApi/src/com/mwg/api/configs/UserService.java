package com.mwg.api.configs;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mwg.api.ApplicationService;

public class UserService implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      AuthUser user = ApplicationService.ins().getUsers().get(username);
      if (user == null) {
        throw new UsernameNotFoundException(username);
      }
      return User.withUsername(username).password(HashUtils.encodeMD5(user.getPassword())).roles(user.getRoles()).build();
    } catch (Exception ex) {
      throw new UsernameNotFoundException(username, ex);
    }
  }
}
