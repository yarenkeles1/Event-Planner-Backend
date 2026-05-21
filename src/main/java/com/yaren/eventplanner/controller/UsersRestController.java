package com.yaren.eventplanner.controller;

import com.yaren.eventplanner.dto.UsersLoginRequestDto;
import com.yaren.eventplanner.dto.UsersRegisterRequestDto;
import com.yaren.eventplanner.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UsersRestController {

    final UsersService usersService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody UsersRegisterRequestDto dto) {
        return usersService.register(dto);
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody UsersLoginRequestDto dto) {
        return usersService.login(dto);
    }

    @GetMapping("logout")
    public ResponseEntity logout() {
        return usersService.logout();
    }

}