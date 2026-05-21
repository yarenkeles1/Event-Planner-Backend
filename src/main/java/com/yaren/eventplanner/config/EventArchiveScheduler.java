package com.yaren.eventplanner.config;

import com.yaren.eventplanner.entity.Event;
import com.yaren.eventplanner.repository.EventRepository;
import com.yaren.eventplanner.util.EventStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventArchiveScheduler {

    private final EventRepository eventRepository;

    // 1. YÖNTEM: Uygulama her ayağa kalktığında 1 kere çalışır.
    @PostConstruct
    @Transactional
    public void archiveOnStartup() {
        System.out.println("The system has started. Checking for past events...");
        archiveExpiredEvents();
    }

    // 2. YÖNTEM: Uygulama açık kaldığı sürece her gece 00:00'da çalışır.
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void archiveExpiredEvents() {
        LocalDate today = LocalDate.now();

        List<Event> expiredEvents = eventRepository.findAll().stream()
                .filter(event -> event.getDate() != null && event.getDate().isBefore(today))
                .filter(event -> event.getStatus() != EventStatus.ARCHIVED)
                .toList();

        if (!expiredEvents.isEmpty()) {
            for (Event event : expiredEvents) {
                event.setStatus(EventStatus.ARCHIVED);
            }
            eventRepository.saveAll(expiredEvents);
            System.out.println(expiredEvents.size() + " past events have been automatically archived.");
        } else {
            System.out.println("No past events were found to archive.");
        }
    }
}