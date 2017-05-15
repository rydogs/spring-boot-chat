package com.rydogs.springboot.chat.web;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rydogs.springboot.chat.data.ParticipantRepository;
import com.rydogs.springboot.chat.data.model.Message;
import com.rydogs.springboot.chat.data.model.User;
import com.rydogs.springboot.chat.service.ChatService;

@Controller
public class ChatController {
  private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

  private final ChatService chatService;
  private final ParticipantRepository participantRepo;

  public ChatController(ChatService chatService, ParticipantRepository participantRepo) {
    this.chatService = chatService;
    this.participantRepo = participantRepo;
  }

  @SubscribeMapping("/participants")
  public Collection<User> retrieveParticipants() {
    return participantRepo.getParticipants();
  }

  @MessageMapping("/chat")
  public void chat(@Payload String message, Principal user) {
    logger.info("Message: {}", message);
    String sender = User.from(user).map(u -> u.getDisplayName()).orElse("Unknown");
    chatService.broadcast(Message.from(sender, message));
  }

  @GetMapping("/user")
  @ResponseBody
  public Principal user(Principal principal) {
    return principal;
  }
}
