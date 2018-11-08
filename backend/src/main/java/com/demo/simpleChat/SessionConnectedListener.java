package com.demo.simpleChat;

import static com.demo.simpleChat.controllers.rest.LobbyController.LOBBY_USER_LEAVE_TOPIC;

import com.demo.simpleChat.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionConnectedListener implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate template;
    private final LobbyService lobbyService;

    @Autowired
    public SessionConnectedListener(SimpMessagingTemplate template, LobbyService lobbyService) {
        this.template = template;
        this.lobbyService = lobbyService;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        String sessionId = sessionDisconnectEvent.getSessionId();
        try {
            template.convertAndSend(LOBBY_USER_LEAVE_TOPIC,
                    lobbyService.userLeaveByTimeout(sessionId));
        } catch (IllegalStateException ignored) {
        }
    }
}