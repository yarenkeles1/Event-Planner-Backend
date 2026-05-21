package com.yaren.eventplanner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.yaren.eventplanner.entity.Users}
 */
@Data
public class UsersLoginRequestDto implements Serializable {
    @NotNull
    @Email
    @NotEmpty
    String email;
    @NotNull
    @Size(min = 6, max = 15)
    @NotEmpty
    String password;
}