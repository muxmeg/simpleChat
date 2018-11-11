package com.demo.simpleChat.model;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ChatMessage {
  private String body;
  private String sender;
  private LocalDateTime date;
}
