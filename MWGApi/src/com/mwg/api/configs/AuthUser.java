package com.mwg.api.configs;

public class AuthUser {
  private String username = "";
  private String password = "";
  private String roles = "USER";
  
  public AuthUser() {
  	
  }

  public AuthUser(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String[] getRoles() {
    return this.roles.split(",");
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

}
