package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.laba5.cars.exceptions.RegistrationException;
import ru.laba5.cars.pojo.*;
import ru.laba5.cars.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> login (@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(new MessageResponse(authService.loginUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signupRequest) throws RegistrationException {
        return ResponseEntity.ok(authService.registerUser(signupRequest));
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Выход выполнен успешно"));
    }

    @GetMapping("/lk")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDataForLk() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getDataForLk());
    }

    @GetMapping("/datetime")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDateTime() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getDataTime());
    }
}
