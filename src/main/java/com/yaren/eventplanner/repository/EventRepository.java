package com.yaren.eventplanner.repository;

import com.yaren.eventplanner.entity.Event;
import com.yaren.eventplanner.entity.Users;
import com.yaren.eventplanner.util.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    Page<Event> findByStatusAndTitleContainsIgnoreCaseOrStatusAndDescriptionContainsIgnoreCaseOrStatusAndLocationContainsIgnoreCaseOrStatusAndCategoryContainsIgnoreCase(
            EventStatus status1, String title,
            EventStatus status2, String description,
            EventStatus status3, String location,
            EventStatus status4, String category,
            Pageable pageable
    );

    Page<Event> findByOwner(Users owner, Pageable pageable);
}