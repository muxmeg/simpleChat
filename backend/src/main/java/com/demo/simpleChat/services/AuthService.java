package com.demo.simpleChat.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  // prototype substitution for real login/registration with DB
  private final Set<String> currentUsers = Collections.synchronizedSet(new HashSet<>());

  /**
   * Login user.
   * @param username name of the user.
   * @return if login was successful or not.
   */
  public boolean loginUser(String username) {
    synchronized (currentUsers) {
      if (currentUsers.contains(username)) {
        return false;
      }
      currentUsers.add(username);
      return true;
    }
  }

  /**
   * Logout user.
   * @param username name of the user.
   */
  public void logoutUser(String username) {
    synchronized (currentUsers) {
      currentUsers.remove(username);
    }
  }
}
