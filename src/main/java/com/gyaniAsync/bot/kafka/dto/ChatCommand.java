package com.gyaniAsync.bot.kafka.dto;

import java.time.Instant;
import java.util.UUID;

public record ChatCommand(
    String jobId,
    String sessionId,
    String message,
    Instant requestedAt
) {
  public static ChatCommand of(String sessionId, String message) {
    return new ChatCommand(UUID.randomUUID().toString(), sessionId, message, Instant.now());
  }
}
