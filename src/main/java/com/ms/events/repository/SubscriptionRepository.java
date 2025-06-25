package com.ms.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.events.model.SubscriptionModel;

public interface SubscriptionRepository extends JpaRepository<SubscriptionModel, Long> {
}
