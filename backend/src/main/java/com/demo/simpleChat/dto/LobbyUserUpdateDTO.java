package com.demo.simpleChat.dto;

import lombok.Value;
import lombok.experimental.Builder;

@Value
@Builder
public class LobbyUserUpdateDTO {
  private String username;
  private boolean joined;
}
