package com.yaren.eventplanner.service;

import com.yaren.eventplanner.dto.*;
import com.yaren.eventplanner.entity.Event;
import com.yaren.eventplanner.entity.Participation;
import com.yaren.eventplanner.entity.Users;
import com.yaren.eventplanner.repository.EventRepository;
import com.yaren.eventplanner.repository.ParticipationRepository;
import com.yaren.eventplanner.repository.UsersRepository;
import com.yaren.eventplanner.util.EventStatus;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    final EventRepository eventRepository;
    final ParticipationRepository participationRepository;
    final ModelMapper model;
    final UsersRepository usersRepository;

    private Users getOwner(HttpSession session) {

        Object obj = session.getAttribute("user");

        if (obj == null) {
            return null;
        }

        if (!(obj instanceof UsersResponseDto dto)) {
            return null;
        }

        return usersRepository.findById(dto.getId())
                .orElse(null);
    }

    public ResponseEntity save(EventSaveRequestDto dto, HttpSession session) {
        Users owner = getOwner(session);
        if (owner == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }
        Event event = model.map(dto, Event.class);
        event.setOwner(owner);
        event.setStatus(EventStatus.ACTIVE);
        eventRepository.save(event);
        return ResponseEntity.ok(Map.of("success", true, "message", "Event created successfully."));
    }

    public ResponseEntity update(EventUpdateRequestDto dto, HttpSession session) {
        Users owner = getOwner(session);
        if (owner == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }
        Optional<Event> optional = eventRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }
        Event event = optional.get();
        if (!event.getOwner().getId().equals(owner.getId())) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "You don't have access to this action."));
        }
        model.map(dto, event);
        eventRepository.save(event);
        return ResponseEntity.ok(Map.of("success", true, "message", "Event updated successfully."));
    }

    public ResponseEntity deleteOne(Long id, HttpSession session) {
        Users owner = getOwner(session);
        if (owner == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }
        Optional<Event> optional = eventRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }
        Event event = optional.get();
        if (!event.getOwner().getId().equals(owner.getId())) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "You don't have access to this action."));
        }

        List<Participation> participation = participationRepository.findByEvent(event);
        if (!participation.isEmpty()) {
            participationRepository.deleteAll(participation);
        }

        eventRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Event deleted successfully."));
    }

    public Page<EventResponseDto> list(int page) {
        Pageable pageable = Pageable.ofSize(12).withPage(page);
        Page<Event> events = eventRepository.findByStatus(EventStatus.ACTIVE, pageable);
        return events.map(event -> {
            EventResponseDto dto = model.map(event, EventResponseDto.class);
            dto.setOwnerFullName(event.getOwner().getFullName());
            dto.setOwnerId(event.getOwner().getId());
            dto.setParticipationCount(participationRepository.countByEvent(event));
            return dto;
        });
    }

    public ResponseEntity getOne(Long id) {
        Optional<Event> optional = eventRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }
        Event event = optional.get();
        EventResponseDto dto = model.map(event, EventResponseDto.class);
        dto.setOwnerFullName(event.getOwner().getFullName());
        dto.setOwnerId(event.getOwner().getId());
        dto.setParticipationCount(participationRepository.countByEvent(event));
        return ResponseEntity.ok(dto);
    }

    public Page<EventResponseDto> search(String q, int page) {
        Pageable pageable = Pageable.ofSize(12).withPage(page);
        Page<Event> events =
                eventRepository.findByStatusAndTitleContainsIgnoreCaseOrStatusAndDescriptionContainsIgnoreCaseOrStatusAndLocationContainsIgnoreCaseOrStatusAndCategoryContainsIgnoreCase(
                        EventStatus.ACTIVE, q,
                        EventStatus.ACTIVE, q,
                        EventStatus.ACTIVE, q,
                        EventStatus.ACTIVE, q,
                        pageable);
        return events.map(event -> {
            EventResponseDto dto = model.map(event, EventResponseDto.class);
            dto.setOwnerFullName(event.getOwner().getFullName());
            dto.setOwnerId(event.getOwner().getId());
            dto.setParticipationCount(participationRepository.countByEvent(event));
            return dto;
        });
    }

    public Page<EventResponseDto> myEvents(int page, HttpSession session) {
        Users owner = getOwner(session);
        if (owner == null) {
            return Page.empty();
        }
        Pageable pageable = Pageable.ofSize(9).withPage(page);
        Page<Event> events = eventRepository.findByOwner(owner, pageable);
        return events.map(event -> {
            EventResponseDto dto = model.map(event, EventResponseDto.class);
            dto.setOwnerFullName(event.getOwner().getFullName());
            dto.setOwnerId(event.getOwner().getId());
            dto.setParticipationCount(participationRepository.countByEvent(event));
            return dto;
        });
    }

    public long countJoinedEvents(HttpSession session) {
        Users user = getOwner(session);
        if (user == null) return 0;
        return participationRepository.countByUser(user);
    }

    public ResponseEntity publish(Long id, HttpSession session) {
        return changeStatus(id, EventStatus.ACTIVE, session);
    }

    public ResponseEntity pause(Long id, HttpSession session) {
        return changeStatus(id, EventStatus.PAUSED, session);
    }

    public ResponseEntity archive(Long id, HttpSession session) {
        return changeStatus(id, EventStatus.ARCHIVED, session);
    }

    private ResponseEntity changeStatus(Long id, EventStatus newStatus, HttpSession session) {
        Users owner = getOwner(session);
        if (owner == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }
        Optional<Event> optional = eventRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }
        Event event = optional.get();
        if (!event.getOwner().getId().equals(owner.getId())) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "You don't have access to this action."));
        }
        event.setStatus(newStatus);
        eventRepository.save(event);
        return ResponseEntity.ok(Map.of("success", true, "message", "Event status has been updated to " + newStatus));
    }
}