package com.demo.simpleChat.services;

import com.demo.simpleChat.dto.ChatMessageDTO;
import com.demo.simpleChat.model.ChatMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

  // prototype container for all lobby data. must be stored on a separate node or distributed cache
  // in the real application
  private final List<String> currentUsers = Collections.synchronizedList(new ArrayList<>());
  private final List<ChatMessage> messages = Collections.synchronizedList(new ArrayList<>());

  private final AuthService authService;

  @Autowired
  public LobbyService(AuthService authService) {
    this.authService = authService;
  }

  /**
   * Add user.
   *
   * @param username user name.
   * @return was user logged in or not.
   */
  public void userJoined(String username) {
    if (!currentUsers.contains(username)) {
      currentUsers.add(username);
    }
  }

  /**
   * Logout user.
   * @param username name of the user.
   */
  public void userLeave(String username) {
    currentUsers.remove(username);
    authService.logoutUser(username);
  }

  /**
   * Find the users in lobby.
   * @return list of usernames.
   */
  public List<String> findUsers() {
    return currentUsers;
  }

  /**
   * Find message history.
   * @param limit limit number of returned records.
   * @return list of chat messages.
   */
  public List<ChatMessage> findMessageHistory(int limit) {
    return messages.subList(Math.max(messages.size() - limit, 0), messages.size());
  }

  /**
   * Listener for new lobby messages.
   * @param messageDTO chat message.
   */
  public void onChatMessageReceive(ChatMessageDTO messageDTO) {
    messages.add(messageDTO.toEntity());
  }

}
