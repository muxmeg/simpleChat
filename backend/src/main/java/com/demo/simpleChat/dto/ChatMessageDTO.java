package com.demo.simpleChat.dto;

import com.demo.simpleChat.model.ChatMessage;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Data for user lobby/chat message.
 */
@Value
@Builder
@AllArgsConstructor
public class ChatMessageDTO {

  private String body;
  private String sender;
  private LocalDateTime date;

  public static ChatMessageDTO fromEntity(ChatMessage chatMessage) {
    return ChatMessageDTO.builder()
        .body(chatMessage.getBody())
        .date(chatMessage.getDate())
        .sender(chatMessage.getSender())
        .build();
  }
}
