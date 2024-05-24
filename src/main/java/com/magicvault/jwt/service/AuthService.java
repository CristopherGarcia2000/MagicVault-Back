package com.magicvault.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.magicvault.documents.Users;
import com.magicvault.jwt.response.AuthResponse;
import com.magicvault.repository.UsersRepository;
import com.magicvault.requests.LoginRequest;
import com.magicvault.requests.RegisterRequest;

import lombok.RequiredArgsConstructor;

// Service class responsible for user authentication and registration.
@Service
@RequiredArgsConstructor
public class AuthService {
    // Autowired instances of required dependencies.
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Method to authenticate a user with the provided credentials and generate a JWT token.
    public AuthResponse login(LoginRequest request) {
        // Authenticate the user with Spring Security's AuthenticationManager.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // Retrieve user details from the repository based on the provided username.
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // Generate a JWT token for the authenticated user.
        String token = jwtService.getToken(user);
        // Return an AuthResponse object containing the generated token.
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    // Method to register a new user with the provided details and generate a JWT token.
    public AuthResponse register(RegisterRequest request) {
        // Create a new user entity using the provided registration details.
        Users user = Users.builder()
            .username(request.getUsername())
            .pass(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .build();
        // Save the new user entity to the repository.
        userRepository.save(user);
        // Generate a JWT token for the registered user.
        String token = jwtService.getToken(user);
        // Return an AuthResponse object containing the generated token.
        return AuthResponse.builder()
            .token(token)
            .build();
    }
}