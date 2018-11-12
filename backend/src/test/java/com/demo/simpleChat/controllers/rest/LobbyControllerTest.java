package com.demo.simpleChat.controllers.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.simpleChat.controllers.websocket.LobbyWSController;
import com.demo.simpleChat.dto.LobbyUserUpdateDTO;
import com.demo.simpleChat.model.ChatMessage;
import com.demo.simpleChat.services.LobbyService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class LobbyControllerTest {

  private MockMvc mockMvc;

  @Mock
  private LobbyService lobbyService;
  @Mock
  private LobbyWSController lobbyWSController;

  @InjectMocks
  private LobbyController controller;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void joinLobby() throws Exception {
    String user = "testUser";

    mockMvc.perform(put("/rest/lobby/users/" + user).contentType(MediaType.APPLICATION_JSON)
            .content("")).andExpect(status().isOk());

    verify(lobbyService, times(1)).userJoined("testUser");
    verify(lobbyWSController, times(1))
        .userStatusUpdate(any(LobbyUserUpdateDTO.class));
    verifyNoMoreInteractions(lobbyService);
  }

  @Test
  public void leaveLobby() throws Exception {
    mockMvc.perform(delete("/rest/lobby/users/testUser").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    verify(lobbyService, times(1)).userLeave("testUser");
    verifyNoMoreInteractions(lobbyService);
  }

  @Test
  public void findLobbyUsers() throws Exception {
    when(lobbyService.findUsers()).thenReturn(Arrays.asList("testUser1", "testUser2"));

    MvcResult mvcResult = mockMvc.perform(get("/rest/lobby/users/")).andExpect(status().isOk())
        .andReturn();

    assertEquals("[\"testUser1\",\"testUser2\"]", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void findLobbyMessages() throws Exception {
    when(lobbyService.findMessageHistory(20)).thenReturn(
        Collections.singletonList(ChatMessage.builder().body("testMessage").build()));

    MvcResult mvcResult = mockMvc.perform(get("/rest/lobby/messages?limit=20"))
        .andExpect(status().isOk())
        .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("\"body\":\"testMessage\""));
  }
}