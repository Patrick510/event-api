package com.ms.events.dto;

import lombok.Data;

@Data
public class EventDTO {

  private int maxParticipants;
  private int registeredParticipants;
  private String date;
  private String title;
  private String description;
  private String[] subscriptions;
}
