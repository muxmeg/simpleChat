package com.demo.simpleChat.controllers.rest;

import static com.demo.simpleChat.AppConfig.REST_SERVICE_PREFIX;

import com.demo.simpleChat.dto.JoinRequestDTO;
import com.demo.simpleChat.services.LobbyService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = REST_SERVICE_PREFIX + "lobby", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class LobbyController {

    private static final String LOBBY_USER_ADD_TOPIC = "/topic/lobby/userJoined";
    public static final String LOBBY_USER_LEAVE_TOPIC = "/topic/lobby/userLeft";

    private final SimpMessagingTemplate template;
    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService, SimpMessagingTemplate template) {
        this.lobbyService = lobbyService;
        this.template = template;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Collection<String> joinLobby(@RequestBody JoinRequestDTO request) {
        template.convertAndSend(LOBBY_USER_ADD_TOPIC, request.getUser());
        return lobbyService.userJoined(request.getUser(), request.getSessionId());
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.DELETE)
    public void leaveLobby(@PathVariable String userName) {
        template.convertAndSend(LOBBY_USER_LEAVE_TOPIC, userName);
        lobbyService.userLeave(userName);
    }
}