package com.demo.simpleChat.dto;

import lombok.Value;

/**
 * Data that user sends to access the lobby.
 */
@Value
public class JoinRequestDTO {
  private String sessionId;
  private String user;
}
