package com.rydogs.springboot.chat.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.rydogs.springboot.chat.data.ParticipantRepository;
import com.rydogs.springboot.chat.data.model.Message;
import com.rydogs.springboot.chat.data.model.User;
import com.rydogs.springboot.chat.service.ChatService;

@Component
public class SessionEventListener {

  private static final Logger logger = LoggerFactory.getLogger(SessionEventListener.class);
  private final ChatService chatService;
  private final ParticipantRepository participantRepo;

  public SessionEventListener(ChatService chatService, ParticipantRepository participantRepo) {
    this.chatService = chatService;
    this.participantRepo = participantRepo;
  }

  @EventListener
  public void onSubscribe(SessionConnectEvent event) {
    User.from(event.getUser())
      .ifPresent(user -> {
        chatService.broadcast(Message.from("System", "Welcome " + user.getDisplayName()));
        participantRepo.add(user);
        chatService.broadcast("/topic/participants", participantRepo.getParticipants());
      });
  }

  @EventListener
  public void onSubscribe(SessionDisconnectEvent event) {
    User.from(event.getUser())
      .ifPresent(user -> {
        chatService.broadcast(Message.from("System", "Goodbye " + user.getDisplayName()));
        participantRepo.remove(user);
        chatService.broadcast("/topic/participants", participantRepo.getParticipants());
      });
  }
}