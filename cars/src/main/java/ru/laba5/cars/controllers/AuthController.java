package ru.laba5.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
