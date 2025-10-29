package com.gyaniAsync.bot.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatResultView(
    String jobId,
    String sessionId,
    String status,
    String content,
    String error,
    Instant timestamp
) {}
