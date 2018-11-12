package com.demo.simpleChat.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AuthServiceTest {

  private AuthService authService;

  @Before
  public void init() {
    authService = new AuthService();
  }

  @Test
  public void loginUser() {
    assertTrue(authService.loginUser("test"));
    assertFalse(authService.loginUser("test"));
    assertTrue(authService.loginUser("test2"));
  }

  @Test
  public void logoutUser() {
    assertTrue(authService.loginUser("test"));
    assertFalse(authService.loginUser("test"));

    authService.logoutUser("test");
    assertTrue(authService.loginUser("test"));
  }
}