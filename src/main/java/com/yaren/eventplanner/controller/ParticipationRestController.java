package com.yaren.eventplanner.controller;

import com.yaren.eventplanner.dto.ParticipationSaveRequestDto;
import com.yaren.eventplanner.service.ParticipationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("participation")
@RequiredArgsConstructor
public class ParticipationRestController {

    final ParticipationService participationService;

    @PostMapping("save")
    public ResponseEntity save(
            @Valid @RequestBody ParticipationSaveRequestDto dto,
            HttpSession session) {
        return participationService.save(dto, session);
    }

    @GetMapping("list/{eventId}")
    public ResponseEntity listByEvent(
            @PathVariable Long eventId,
            HttpSession session) {
        return participationService.listByEvent(eventId, session);
    }
    @GetMapping("check/{eventId}")
    public ResponseEntity<Boolean> checkParticipation(
            @PathVariable Long eventId,
            HttpSession session) {
        return participationService.checkParticipation(eventId, session);
    }

    @GetMapping("my-joined")
    public ResponseEntity getMyJoinedEvents(
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return participationService.getMyJoinedEvents(session, pageable);
    }

    @GetMapping("control")
    public void control() {}
}
