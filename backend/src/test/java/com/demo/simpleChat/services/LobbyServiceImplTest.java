package com.demo.simpleChat.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LobbyServiceImplTest {

  @Mock
  private AuthService authService;

  @InjectMocks
  private LobbyService lobbyService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void userJoined() throws Exception {
    lobbyService.userJoined("testUser");
    List<String> users = lobbyService.findUsers();
    assertEquals(1, users.size());
    assertEquals(1, users.stream().filter(user -> user.equals("testUser")).toArray().length);

    lobbyService.userJoined("testUser2");
    users = lobbyService.findUsers();
    assertEquals(2, users.size());
    assertEquals(1, users.stream().filter(user -> user.equals("testUser2")).toArray().length);

    lobbyService.userJoined("testUser2");
    users = lobbyService.findUsers();
    assertEquals(2, users.size());
  }

  @Test
  public void userLeave() throws Exception {
    lobbyService.userJoined("testUser");
    lobbyService.userJoined("testUser2");
    lobbyService.userJoined("testUser3");

    lobbyService.userLeave("testUser3");
    lobbyService.userLeave("testUser3");
    lobbyService.userLeave("testUser2");
    lobbyService.userJoined("testUser3");

    List<String> users = lobbyService.findUsers();
    assertEquals(2, users.size());
    assertTrue(users.contains("testUser"));
    verify(authService, times(2)).logoutUser("testUser3");
    verify(authService, times(1)).logoutUser("testUser2");
  }

}