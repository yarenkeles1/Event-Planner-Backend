package com.yaren.eventplanner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Participation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationSaveRequestDto implements Serializable {
    @NotNull
    @Min(1)
    Long eventId;
}