package com.ms.events.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.ms.events.service.EventService;

@RestController
@RequestMapping("/events")
public class EventsController {

  @Autowired
  private EventService eventService;
}
