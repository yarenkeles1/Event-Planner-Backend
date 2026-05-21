package com.yaren.eventplanner.dto;

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
public class ParticipationResponseDto implements Serializable {
    private Long id;
    private Long userId;
    private String userFullName;
    private Long eventId;
    private String eventTitle;

}