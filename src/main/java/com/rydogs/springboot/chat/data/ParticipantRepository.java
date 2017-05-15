package com.rydogs.springboot.chat.data;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.rydogs.springboot.chat.data.model.User;

/**
 * Using Hazelcast for distributed persistence for participants
 */
@Component
public class ParticipantRepository {
  private static final String PARTICIPANTS = "chat_participants";
  private final IMap<String, User> participants;

  public ParticipantRepository(HazelcastInstance hazelcast) {
    this.participants = hazelcast.getMap(PARTICIPANTS);
  }

  public void add(User user) {
    participants.put(user.getId(), user);
  }

  public void remove(User user) {
    participants.remove(user.getId());
  }

  public Collection<User> getParticipants() {
    return participants.values();
  }
}
