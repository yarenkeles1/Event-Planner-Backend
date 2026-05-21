package com.yaren.eventplanner.service;

import com.yaren.eventplanner.dto.ParticipationResponseDto;
import com.yaren.eventplanner.dto.ParticipationSaveRequestDto;
import com.yaren.eventplanner.dto.UsersResponseDto;
import com.yaren.eventplanner.entity.Event;
import com.yaren.eventplanner.entity.Participation;
import com.yaren.eventplanner.entity.Users;
import com.yaren.eventplanner.repository.EventRepository;
import com.yaren.eventplanner.repository.ParticipationRepository;
import com.yaren.eventplanner.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    final ParticipationRepository participationRepository;
    final EventRepository eventRepository;
    final UsersRepository usersRepository;

    public ResponseEntity save(ParticipationSaveRequestDto dto, HttpSession session) {
        Object sessionObj = session.getAttribute("user");
        if (sessionObj == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }

        UsersResponseDto sessionDto = (UsersResponseDto) sessionObj;
        Users user = usersRepository.findById(sessionDto.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "User not found, please sign in again."));
        }

        Optional<Event> optionalEvent = eventRepository.findById(dto.getEventId());
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }

        Event event = optionalEvent.get();

        if (event.getOwner().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "As the event organizer, you cannot register as a participant."));
        }

        Optional<Participation> existing = participationRepository.findByUserAndEvent(user, event);
        if (existing.isPresent()) {
            return ResponseEntity.status(409).body(Map.of("success", false, "message", "You are already registered for this event."));
        }

        Participation participation = new Participation();
        participation.setUser(user);
        participation.setEvent(event);
        participationRepository.save(participation);

        return ResponseEntity.ok(Map.of("success", true, "message", "You have successfully registered for the event."));
    }

    public ResponseEntity listByEvent(Long eventId, HttpSession session) {
        Object sessionObj = session.getAttribute("user");
        if (sessionObj == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }

        UsersResponseDto sessionDto = (UsersResponseDto) sessionObj;
        Users user = usersRepository.findById(sessionDto.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "User not found, please sign in again."));
        }

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Event not found."));
        }

        Event event = optionalEvent.get();

        if (!event.getOwner().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "You don't have access to this action."));
        }

        List<Participation> participations = participationRepository.findByEvent(event);
        List<ParticipationResponseDto> result = participations.stream()
                .map(p -> {
                    ParticipationResponseDto responseDto = new ParticipationResponseDto();
                    responseDto.setId(p.getId());
                    responseDto.setUserId(p.getUser().getId());
                    responseDto.setUserFullName(p.getUser().getFullName());
                    responseDto.setEventId(p.getEvent().getId());
                    responseDto.setEventTitle(p.getEvent().getTitle());
                    return responseDto;
                })
                .toList();

        return ResponseEntity.ok(result);
    }
    public ResponseEntity<Boolean> checkParticipation(Long eventId, HttpSession session) {
        Object sessionObj = session.getAttribute("user");
        if (sessionObj == null) {
            return ResponseEntity.ok(false);
        }

        UsersResponseDto sessionDto = (UsersResponseDto) sessionObj;
        Users user = usersRepository.findById(sessionDto.getId()).orElse(null);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (user == null || event == null) {
            return ResponseEntity.ok(false);
        }

        boolean isJoined = participationRepository.findByUserAndEvent(user, event).isPresent();

        return ResponseEntity.ok(isJoined);
    }
    public ResponseEntity getMyJoinedEvents(HttpSession session, Pageable pageable) {
        Object sessionObj = session.getAttribute("user");
        if (sessionObj == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please sign in to continue."));
        }

        UsersResponseDto sessionDto = (UsersResponseDto) sessionObj;
        Users user = usersRepository.findById(sessionDto.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "User not found, please sign in again."));
        }

        Page<Participation> participations = participationRepository.findByUser(user, pageable);

        Page<com.yaren.eventplanner.dto.EventResponseDto> joinedEventsPage = participations.map(p -> {
            Event e = p.getEvent();
            com.yaren.eventplanner.dto.EventResponseDto dto = new com.yaren.eventplanner.dto.EventResponseDto();
            dto.setId(e.getId());
            dto.setTitle(e.getTitle());
            dto.setDescription(e.getDescription());
            dto.setDate(e.getDate());
            dto.setTime(e.getTime());
            dto.setLocation(e.getLocation());
            dto.setCategory(e.getCategory());
            dto.setStatus(e.getStatus());
            return dto;
        });

        return ResponseEntity.ok(joinedEventsPage);
    }
}