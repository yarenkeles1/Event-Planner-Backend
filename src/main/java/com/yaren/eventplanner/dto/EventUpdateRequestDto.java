package com.yaren.eventplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Event}
 */
@Data
public class EventUpdateRequestDto implements Serializable {
    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    Long id;
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
    @FutureOrPresent
    LocalDate date;
    @NotNull
    LocalTime time;
    private String imageUrl;
}