package com.demo.simpleChat.controllers.websocket;

import com.demo.simpleChat.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyWSController {

    private static final String LOBBY_MESSAGING_MAPPING = "/lobbyMessage";
    private static final String LOBBY_MESSAGING_TOPIC = "/topic/lobbyMessages";

    private final SimpMessagingTemplate template;

    @Autowired
    public LobbyWSController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(LOBBY_MESSAGING_MAPPING)
    @SendTo(LOBBY_MESSAGING_TOPIC)
    public ChatMessageDTO sendMessage(ChatMessageDTO message) {
        return message;
    }
}
