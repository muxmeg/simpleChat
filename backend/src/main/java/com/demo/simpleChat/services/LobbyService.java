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
  ;

  private final AuthService authService;

  @Autowired
  public LobbyService(AuthService authService) {
    this.authService = authService;
  }

  /**
   * Login user.
   *
   * @param userName user name.
   * @return was user logged in or not.
   */
  public void userJoined(String userName) { // return sessionID here
    currentUsers.add(userName);
  }

  /**
   * {@inheritDoc}
   */
  public void userLeave(String userName) {
    currentUsers.remove(userName);
    authService.logoutUser(userName);
  }

  public List<String> findUsers() {
    return currentUsers;
  }

  public List<ChatMessage> findMessageHistory(int limit) {
    return messages.subList(Math.max(messages.size() - limit, 0), messages.size());
  }

  public void onChatMessageReceive(ChatMessageDTO messageDTO) {
    messages.add(messageDTO.toEntity());
  }

}
