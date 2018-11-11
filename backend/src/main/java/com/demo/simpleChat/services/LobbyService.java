package com.demo.simpleChat.services;

import com.demo.simpleChat.dto.ChatMessageDTO;
import com.demo.simpleChat.model.ChatMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

  //prototype container for all lobby data. must be stored on a separate node in the real application
  private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();
  private final List<ChatMessage> messages = Collections.synchronizedList(new ArrayList<>());;

  private final AuthService authService;

  @Autowired
  public LobbyService(AuthService authService) {
    this.authService = authService;
  }

  /**
   * Login user.
   *
   * @param user user name.
   * @return was user logged in or not.
   */
  public void userJoined(String userName, String sessionId) { // return sessionID here
    users.put(userName, sessionId);
  }

  /**
   * {@inheritDoc}
   */
  public String userLeaveByTimeout(String sessionId) throws IllegalStateException {
    String userName;
    Optional<Map.Entry<String, String>> optionalEntry = users.entrySet().stream()
        .filter(entry -> entry.getValue().equals(sessionId))
        .findFirst(); //looking for user entry by session id
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

  public List<String> findUsers() {
    return new ArrayList<>();
  }

  public List<ChatMessage> findMessageHistory(int limit) {
    return messages.subList(Math.max(messages.size() - limit, 0), messages.size());
  }

  public void onChatMessageReceive(ChatMessageDTO messageDTO) {
    messages.add(messageDTO.toEntity());
  }

}
