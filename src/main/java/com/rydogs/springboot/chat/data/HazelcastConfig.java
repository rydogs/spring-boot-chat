package com.rydogs.springboot.chat.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;

@Configuration
public class HazelcastConfig {
  @Bean
  @Autowired
  public Config hazelcastCustomConfig(@Value("#{'${hazelcast.members:localhost}'.split(',')}") List<String> members) {
    Config config = new Config();
    config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    members.forEach(member -> config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(member));
    config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
    config.setProperty("hazelcast.logging.type", "slf4j");
    return config;
  }
}
