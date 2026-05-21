package com.yaren.eventplanner.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Users}
 */
@Data
public class UsersResponseDto implements Serializable {
    Long id;
    String fullName;
    String email;
}