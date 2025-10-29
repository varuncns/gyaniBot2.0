package com.gyaniAsync.bot.kafka;

import com.gyaniAsync.bot.kafka.dto.ChatCommand;
import com.gyaniAsync.bot.kafka.dto.ChatResult;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatWorker {

  private final KafkaTemplate<String, Object> template;
  @Value("${app.kafka.topic.out}") String outTopic;

  @KafkaListener(topics = "${app.kafka.topic.in}")
  public void onCommand(ChatCommand cmd) {
    try {
      // Simple echo logic for Step 1
      String reply = "You said: " + cmd.message();
      var result = new ChatResult(cmd.jobId(), cmd.sessionId(), reply, Instant.now(), true, null);
      template.send(outTopic, cmd.jobId(), result);
    } catch (Exception e) {
      var result = new ChatResult(cmd.jobId(), cmd.sessionId(), null, Instant.now(), false, e.getMessage());
      template.send(outTopic, cmd.jobId(), result);
    }
  }
}
