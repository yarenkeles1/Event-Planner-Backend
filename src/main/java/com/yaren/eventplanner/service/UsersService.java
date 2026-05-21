package com.yaren.eventplanner.service;

import com.yaren.eventplanner.dto.UsersLoginRequestDto;
import com.yaren.eventplanner.dto.UsersRegisterRequestDto;
import com.yaren.eventplanner.dto.UsersResponseDto;
import com.yaren.eventplanner.entity.Users;
import com.yaren.eventplanner.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    final UsersRepository usersRepository;
    final HttpServletRequest request;
    final ModelMapper model;

    public ResponseEntity register(UsersRegisterRequestDto dto) {
        if (usersRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "This email is already in use.")
            );
        }

        Users user = model.map(dto, Users.class);
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        usersRepository.save(user);

        UsersResponseDto responseDto = model.map(user, UsersResponseDto.class);
        return ResponseEntity.ok().body(responseDto);
    }

    public ResponseEntity login(UsersLoginRequestDto dto) {
        Optional<Users> optionalUser = usersRepository.findByEmailIgnoreCase(dto.getEmail());

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            boolean isMatch = BCrypt.checkpw(dto.getPassword(), user.getPassword());

            if (isMatch) {
                UsersResponseDto responseDto = model.map(user, UsersResponseDto.class);
                request.getSession().setAttribute("user", responseDto);
                return ResponseEntity.ok(responseDto);
            }
        }
        return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", "Invalid email or password.")
        );
    }

    public ResponseEntity logout() {
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("success", true, "message", "Logged out successfully."));
    }
}