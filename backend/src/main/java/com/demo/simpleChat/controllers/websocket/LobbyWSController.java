package com.demo.simpleChat.controllers.websocket;

import com.demo.simpleChat.dto.ChatMessageDTO;
import com.demo.simpleChat.dto.LobbyUserUpdateDTO;
import com.demo.simpleChat.services.LobbyService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyWSController {

  private static final String LOBBY_MESSAGING_MAPPING = "/lobbyMessage";
  private static final String LOBBY_MESSAGING_TOPIC = "/topic/lobbyMessages";
  private static final String LOBBY_USERS_TOPIC = "/topic/lobbyUsers";

  private final SimpMessagingTemplate template;
  private final LobbyService lobbyService;

  public LobbyWSController(SimpMessagingTemplate template,
      LobbyService lobbyService) {
    this.template = template;
    this.lobbyService = lobbyService;
  }

  @MessageMapping(LOBBY_MESSAGING_MAPPING)
  @SendTo(LOBBY_MESSAGING_TOPIC)
  public ChatMessageDTO sendMessage(ChatMessageDTO message) {
    lobbyService.onChatMessageReceive(message);
    return message;
  }

  public void userStatusUpdate(LobbyUserUpdateDTO userUpdateDTO) {
    template.convertAndSend(LOBBY_USERS_TOPIC, userUpdateDTO);
  }
}
