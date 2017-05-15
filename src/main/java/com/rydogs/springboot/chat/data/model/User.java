package com.rydogs.springboot.chat.data.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class User implements Serializable {
  private static final long serialVersionUID = -1081294285155775418L;
  private String id;
  private UserType type;
  private String displayName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public enum UserType {Facebook, Google}

  public static Optional<User> from(Principal principal) {
    return Optional.ofNullable(principal).map(user -> OAuth2Authentication.class.cast(user))
      .map(user -> (HashMap<String, String>)user.getUserAuthentication().getDetails())
      .map(detail -> {
        User user = new User();
        user.setId(detail.get("id"));
        user.setDisplayName(detail.get("name"));
        user.setType(UserType.Facebook);
        return user;
      });
  }
}
