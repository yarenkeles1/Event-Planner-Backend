package com.yaren.eventplanner.repository;

import com.yaren.eventplanner.entity.Event;
import com.yaren.eventplanner.entity.Participation;
import com.yaren.eventplanner.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByEvent(Event event);

    Optional<Participation> findByUserAndEvent(Users user, Event event);

    long countByEvent(Event event);

    Page<Participation> findByUser(Users user, Pageable pageable);

}