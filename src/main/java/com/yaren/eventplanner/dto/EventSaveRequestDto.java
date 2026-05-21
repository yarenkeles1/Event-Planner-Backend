package com.yaren.eventplanner.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Event}
 */
@Data
public class EventSaveRequestDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 200)
    @NotEmpty
    String title;
    @NotNull
    @Size(min = 2, max = 200)
    @NotEmpty
    String location;
    @NotNull
    @Size(min = 2, max = 1000)
    @NotEmpty
    String description;
    @NotNull
    @Size(min = 2, max = 50)
    @NotEmpty
    String category;
    @NotNull
    @FutureOrPresent(message = "Events cannot be scheduled for a past date. Please select a future date.")
    LocalDate date;
    @NotNull
    LocalTime time;
    private String imageUrl;
}