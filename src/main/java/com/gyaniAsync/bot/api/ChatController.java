package com.gyaniAsync.bot.api;

import com.gyaniAsync.bot.api.dto.ChatResultView;
import com.gyaniAsync.bot.core.JobStore;
import com.gyaniAsync.bot.kafka.ChatProducer;
import com.gyaniAsync.bot.kafka.dto.ChatCommand;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatProducer producer;
  private final JobStore store;

  public ChatController(ChatProducer producer, JobStore store) {
    this.producer = producer;
    this.store = store;
  }

  public record ChatRequest(@NotBlank String message, String sessionId) {}

  @PostMapping("/message")
  public ResponseEntity<?> message(@RequestBody ChatRequest body) {
    var sid = (body.sessionId() == null || body.sessionId().isBlank())
        ? UUID.randomUUID().toString()
        : body.sessionId();

    var cmd = ChatCommand.of(sid, body.message());
    producer.enqueue(cmd);
    return ResponseEntity.accepted().body(Map.of("jobId", cmd.jobId(), "sessionId", sid));
  }

  @GetMapping("/result/{jobId}")
  public ResponseEntity<?> result(@PathVariable String jobId) {
    var res = store.get(jobId);
    if (res == null) {
      return ResponseEntity.status(HttpStatus.ACCEPTED)
          .body(new ChatResultView(jobId, null, "PENDING", null, null, null));
    }
    return ResponseEntity.ok(new ChatResultView(
        res.jobId(),
        res.sessionId(),
        res.success() ? "DONE" : "ERROR",
        res.content(),
        res.error(),
        res.completedAt()
    ));
  }


}
