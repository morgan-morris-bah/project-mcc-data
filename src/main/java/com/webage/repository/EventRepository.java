package com.webage.repository;

import org.springframework.data.repository.CrudRepository;

import com.webage.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
