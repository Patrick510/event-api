package com.ms.events.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ms.events.model.EventModel;
import com.ms.events.model.SubscriptionModel;
import com.ms.events.repository.EventRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

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
    subscription.setEvent(event);
    return subscriptionRepository.save(subscription);
  }
}
