package com.rydogs.springboot.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class Application {
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    LOGGER.info("Application started....");
  }
}
