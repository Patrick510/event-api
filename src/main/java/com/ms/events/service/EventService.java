package com.ms.events.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.events.messaging.EmailMessageProducer;
import com.ms.events.model.EventModel;
import com.ms.events.model.SubscriptionModel;
import com.ms.events.repository.EventRepository;
import com.ms.events.repository.SubscriptionRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private AmqpTemplate amqpTemplate;

  @Autowired
  private EmailMessageProducer emailMessageProducer;

  public List<EventModel> getAllEvents() {
    return eventRepository.findAll();
  }

  public List<EventModel> getUpcommingEvents() {
    return eventRepository.findByDateAfter(new Date());
  }

  public EventModel createEvent(EventModel event) {
    return eventRepository.save(event);
  }

  public SubscriptionModel registerParticipant(Long eventId, SubscriptionModel subscription) {
    EventModel event = eventRepository.findById(eventId)
        .orElseThrow(() -> new RuntimeException("Event not found"));

    if (event.getRegisteredParticipants() >= event.getMaxParticipants()) {
      throw new RuntimeException("Event is full");
    }

    subscription.setEvent(event);
    SubscriptionModel savedSubscription = subscriptionRepository.save(subscription);

    event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);
    eventRepository.save(event);

    emailMessageProducer.sendEmailMessage(
        subscription.getParticipantEmail(),
        event.getTitle(),
        event.getDate());

    return savedSubscription;
  }
}
