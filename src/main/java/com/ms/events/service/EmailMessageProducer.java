package com.ms.events.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.events.configuration.RabbitMQConfig;

@Service
public class EmailMessageProducer {

  @Autowired
  private AmqpTemplate rabbitTemplate;

  public void sendEmailMessage(String email, String title, String date) {
    Map<String, Object> message = new HashMap<>();
    message.put("email", email);
    message.put("title", title);
    message.put("date", date);

    rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
  }
}
