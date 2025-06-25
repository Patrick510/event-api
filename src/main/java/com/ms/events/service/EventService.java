package com.ms.events.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<EventModel> getAllEvents() {
    return eventRepository.findAll();
  }

  public List<EventModel> getUpcommingEvents() {
    return eventRepository.findByEventDateAfter(LocalDate.now());
  }

  public EventModel createEvent(EventModel event) {
    return eventRepository.save(event);
  }

  public SubscriptionModel registerParticipant(Long eventId, SubscriptionModel subscription) {
    EventModel event = eventRepository.findById(eventId)
        .orElseThrow(() -> new RuntimeException("Event not found"));

    if (event.getRegisteredParticipants() >= event.getMaxParticipants()) {
      throw new RuntimeException("Cannot register for past events");
    }
    subscription.setEvent(event);
    SubscriptionModel savedSubscription = subscriptionRepository.save(subscription);
    event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);
    eventRepository.save(event);

    return savedSubscription;
  }
}
