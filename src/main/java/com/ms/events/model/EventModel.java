package com.ms.events.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.TemporalType;
import lombok.Data;
import jakarta.persistence.Temporal;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotBlank;

@Entity
@Data
public class EventModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Min(1)
  private int maxParticipants;

  private int registeredParticipants;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  // @NotBlank
  private String title;
  private String description;

  @OneToMany(mappedBy = "event")
  @JsonManagedReference
  private List<SubscriptionModel> subscriptions = new ArrayList<>();

}
