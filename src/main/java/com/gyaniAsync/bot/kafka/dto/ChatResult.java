package com.gyaniAsync.bot.kafka.dto;

import java.time.Instant;

public record ChatResult(
    String jobId,
    String sessionId,
    String content,
    Instant completedAt,
    boolean success,
    String error
) {}
