package com.demo.simpleChat.dto;

import java.time.LocalDateTime;
import lombok.Value;

/**
 * Data for user lobby/chat message.
 */
@Value
public class ChatMessageDTO {
  private String message;
  private String sender;
  private LocalDateTime date;
}
