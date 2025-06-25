package com.ms.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.events.model.EventModel;
import com.ms.events.model.SubscriptionModel;
import com.ms.events.service.EventService;

@RestController
@RequestMapping("/events")
public class EventsController {

  @Autowired
  private EventService eventService;

  @GetMapping
  public List<EventModel> getEvents() {
    return eventService.getAllEvents(); // This should be replaced with actual logic to return events
  }

  @GetMapping("/upcoming")
  public List<EventModel> getUpcomingEvents() {
    return eventService.getUpcommingEvents(); // This should be replaced with actual logic to return upcoming events
  }

  @PostMapping
  public EventModel createEvent(EventModel event) {
    return eventService.createEvent(event); // This should be replaced with actual logic to create an event
  }

  @PostMapping("/{eventId}/register")
  public SubscriptionModel registerParticipant(@PathVariable Long eventId, SubscriptionModel subscription) {
    return eventService.registerParticipant(eventId, subscription); // This should be replaced with actual logic to
                                                                    // register a participant
  }

}
