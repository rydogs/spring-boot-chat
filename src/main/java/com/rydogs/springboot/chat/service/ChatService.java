package com.rydogs.springboot.chat.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.rydogs.springboot.chat.data.model.Message;

@Service
public class ChatService {
  private final SimpMessagingTemplate messagingTemplate;

  public ChatService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public void send(String user, Message message) {
    messagingTemplate.convertAndSendToUser(user, "/topic/messages", message);
  }

  public void broadcast(Object message) {
    broadcast("/topic/messages", message);
  }

  public void broadcast(String topic, Object message) {
    messagingTemplate.convertAndSend(topic, message);
  }
}
