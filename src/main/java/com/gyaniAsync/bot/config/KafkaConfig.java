package com.gyaniAsync.bot.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

  @Value("${app.kafka.topic.in}") private String inTopic;
  @Value("${app.kafka.topic.out}") private String outTopic;

  @Bean public NewTopic chatIn()  { return TopicBuilder.name(inTopic).partitions(3).replicas(1).build(); }
  @Bean public NewTopic chatOut() { return TopicBuilder.name(outTopic).partitions(3).replicas(1).build(); }
}

