package com.yaren.eventplanner.controller;

import com.yaren.eventplanner.dto.EventSaveRequestDto;
import com.yaren.eventplanner.dto.EventUpdateRequestDto;
import com.yaren.eventplanner.dto.EventResponseDto;
import com.yaren.eventplanner.service.EventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
public class EventRestController {

    final EventService eventService;

    @PostMapping("save")
    public ResponseEntity save(
            @Valid @RequestBody EventSaveRequestDto dto,
            HttpSession session) {
        return eventService.save(dto, session);
    }

    @PutMapping("update")
    public ResponseEntity update(
            @Valid @RequestBody EventUpdateRequestDto dto,
            HttpSession session) {
        return eventService.update(dto, session);
    }

    @DeleteMapping("deleteOne/{id}")
    public ResponseEntity deleteOne(
            @PathVariable Long id,
            HttpSession session) {
        return eventService.deleteOne(id, session);
    }

    @GetMapping("list")
    public Page<EventResponseDto> list(
            @RequestParam(defaultValue = "0") int page) {
        return eventService.list(page);
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return eventService.getOne(id);
    }

    @GetMapping("search")
    public Page<EventResponseDto> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page) {
        return eventService.search(q, page);
    }

    @GetMapping("myEvents")
    public Page<EventResponseDto> myEvents(
            @RequestParam(defaultValue = "0") int page,
            HttpSession session) {
        return eventService.myEvents(page, session);
    }

    @PatchMapping("publish/{id}")
    public ResponseEntity publish(
            @PathVariable Long id,
            HttpSession session) {
        return eventService.publish(id, session);
    }

    @PatchMapping("pause/{id}")
    public ResponseEntity pause(
            @PathVariable Long id,
            HttpSession session) {
        return eventService.pause(id, session);
    }

    @PatchMapping("archive/{id}")
    public ResponseEntity archive(
            @PathVariable Long id,
            HttpSession session) {
        return eventService.archive(id, session);
    }
    @GetMapping("control")
    public void control() {}
}