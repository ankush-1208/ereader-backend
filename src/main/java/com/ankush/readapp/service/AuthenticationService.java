package com.ankush.readapp.service;

import com.ankush.readapp.dto.AuthenticationResponse;
import com.ankush.readapp.dto.UserAuthenticationRequest;
import com.ankush.readapp.dto.UserRegistrationRequest;
import com.ankush.readapp.enums.Role;
import com.ankush.readapp.exception.BadRequestException;
import com.ankush.readapp.mapper.UserMapper;
import com.ankush.readapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(UserRegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("A user already exists with the provided email");
        }
        var user = UserMapper.INSTANCE.toEntity(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticateUser(UserAuthenticationRequest userAuthenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getEmail(),
                        userAuthenticationRequest.getPassword())
        );

        var user = userRepository.findByEmail(userAuthenticationRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("The User does not exist"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
