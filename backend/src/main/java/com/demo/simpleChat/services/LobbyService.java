package com.demo.simpleChat.services;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

    //prototype container for all lobby data. must be stored on a separate node in the real application
    private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    private final AuthService authService;

    @Autowired
    public LobbyService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<String> userJoined(String userName, String sessionId) {
        users.put(userName, sessionId);
        return users.keySet();
    }

    /**
     * {@inheritDoc}
     */
    public String userLeaveByTimeout(String sessionId) throws IllegalStateException {
        String userName;
        Optional<Map.Entry<String, String>> optionalEntry = users.entrySet().stream()
                .filter(entry -> entry.getValue().equals(sessionId)).findFirst(); //looking for user entry by session id
        if (!optionalEntry.isPresent()) {
            throw new IllegalStateException();
        }
        userName = optionalEntry.get().getKey();
        users.remove(userName);
        authService.logoutUser(userName);
        return userName;
    }

    /**
     * {@inheritDoc}
     */
    public void userLeave(String userName) {
        users.remove(userName);
        authService.logoutUser(userName);
    }
}
