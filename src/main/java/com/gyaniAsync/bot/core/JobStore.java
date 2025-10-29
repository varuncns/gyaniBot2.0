package com.gyaniAsync.bot.core;

import com.gyaniAsync.bot.kafka.dto.ChatResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobStore {
  private final Map<String, ChatResult> results = new ConcurrentHashMap<>();

  public ChatResult get(String jobId) { return results.get(jobId); }

  @KafkaListener(topics = "${app.kafka.topic.out}")
  public void onResult(ChatResult r) { results.put(r.jobId(), r); }
}
