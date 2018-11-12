package com.demo.simpleChat.controllers.rest;

import static com.demo.simpleChat.AppConfig.REST_SERVICE_PREFIX;

import com.demo.simpleChat.controllers.websocket.LobbyWSController;
import com.demo.simpleChat.dto.ChatMessageDTO;
import com.demo.simpleChat.dto.LobbyUserUpdateDTO;
import com.demo.simpleChat.services.LobbyService;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = REST_SERVICE_PREFIX
    + "lobby", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class LobbyController {

  private final SimpMessagingTemplate template;
  private final LobbyService lobbyService;
  private final LobbyWSController lobbyWSController;

  public LobbyController(LobbyService lobbyService, SimpMessagingTemplate template,
      LobbyWSController lobbyWSController) {
    this.lobbyService = lobbyService;
    this.template = template;
    this.lobbyWSController = lobbyWSController;
  }

  @PutMapping(value = "/users/{username}")
  public void joinLobby(@PathVariable String username) {

    lobbyService.userJoined(username);
    lobbyWSController.userStatusUpdate(LobbyUserUpdateDTO.builder()
        .username(username)
        .joined(true)
        .build());
  }

  @GetMapping(value = "/users")
  public Collection<String> findLobbyUsers() {
    return lobbyService.findUsers();
  }

  @GetMapping(value = "/messages")
  public Collection<ChatMessageDTO> findSavedMessages(@RequestParam(required = false,
      defaultValue = "0") int limit) {
    return lobbyService.findMessageHistory(limit).stream()
        .map(ChatMessageDTO::fromEntity)
        .collect(Collectors.toList());
  }

  @DeleteMapping(value = "/users/{username}")
  public void leaveLobby(@PathVariable String username) {
    lobbyWSController.userStatusUpdate(LobbyUserUpdateDTO.builder()
        .username(username)
        .joined(true)
        .build());
    lobbyService.userLeave(username);
  }
}