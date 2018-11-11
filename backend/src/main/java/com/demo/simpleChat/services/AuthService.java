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
   * {@inheritDoc}
   */
  public boolean loginUser(String user) {
    synchronized (currentUsers) {
      if (currentUsers.contains(user)) {
        return false;
      }
      currentUsers.add(user);
      return true;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void logoutUser(String user) {
    synchronized (currentUsers) {
      currentUsers.remove(user);
    }
  }
}
