package com.yaren.eventplanner.dto;

import com.yaren.eventplanner.util.EventStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Event}
 */
@Data
public class EventResponseDto implements Serializable {
    Long id;
    String title;
    String location;
    String description;
    String category;
    LocalDate date;
    LocalTime time;
    Long ownerId;
    String ownerFullName;
    long participationCount;
    EventStatus status;
    private String imageUrl;
}