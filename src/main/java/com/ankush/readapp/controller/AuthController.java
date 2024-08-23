package com.ankush.readapp.controller;

import com.ankush.readapp.dto.UserAuthenticationRequest;
import com.ankush.readapp.dto.AuthenticationResponse;
import com.ankush.readapp.dto.UserRegistrationRequest;
import com.ankush.readapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("Received request to register new user");
        return ResponseEntity.ok(authService.registerUser(userRegistrationRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        log.info("Received request to authenticate user with email");
        return ResponseEntity.ok(authService.authenticateUser(userAuthenticationRequest));
    }
}
