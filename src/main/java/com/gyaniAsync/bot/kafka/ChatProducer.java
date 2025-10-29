package com.gyaniAsync.bot.kafka;

import com.gyaniAsync.bot.kafka.dto.ChatCommand;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatProducer {
  private final KafkaTemplate<String, Object> template;
  private final String inTopic;

  public ChatProducer(KafkaTemplate<String, Object> template,
                      @Value("${app.kafka.topic.in}") String inTopic) {
    this.template = template;
    this.inTopic = inTopic;
  }

  public String enqueue(ChatCommand cmd) {
    template.send(inTopic, cmd.jobId(), cmd);
    return cmd.jobId();
  }
}
