package com.demo.simpleChat.model;

import java.time.LocalDateTime;
import lombok.Value;
import lombok.experimental.Builder;

@Value
@Builder
public class ChatMessage {
  private String body;
  private String sender;
  private LocalDateTime date;
}
