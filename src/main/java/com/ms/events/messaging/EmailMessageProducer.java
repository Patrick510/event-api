package com.ms.events.messaging;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageProducer {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  ppublic void sendEmailMessage(String emailTo, String eventTitle, Date eventDate) {
    Map<String, Object> message = new HashMap<>();
    message.put("emailTo", emailTo);
    message.put("eventTitle", eventTitle);
    message.put("eventDate", eventDate);

    rabbitTemplate.convertAndSend("email-exchange", "email-routing-key", message);
  }
}
