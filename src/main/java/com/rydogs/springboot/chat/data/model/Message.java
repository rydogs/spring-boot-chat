package com.rydogs.springboot.chat.data.model;

public class Message {
  private final String message;
  private final String sender;

  Message(String sender, String message) {
    this.message = message;
    this.sender = sender;
  }

  public String getMessage() {
    return message;
  }

  public String getSender() {
    return sender;
  }

  public static Message from(String sender, String message) {
    return new Message(sender, message);
  }
}
