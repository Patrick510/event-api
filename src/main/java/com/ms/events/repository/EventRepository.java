package com.ms.events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.events.model.EventModel;

public interface EventRepository extends JpaRepository<EventModel, Long> {

  List<EventModel> findByTitleContainingIgnoreCase(String title);

  List<EventModel> findByDate(String date);

  List<EventModel> findByTitleContainingIgnoreCaseAndDate(String title, String date);

  List<EventModel> findByEventDateAfter(java.time.LocalDate date);
}
