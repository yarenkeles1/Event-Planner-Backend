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
public class UsersRegisterRequestDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String fullName;
    @NotNull
    @Email
    @NotEmpty
    String email;
    @NotNull
    @Size(min = 6, max = 15)
    @NotEmpty
    String password;
}