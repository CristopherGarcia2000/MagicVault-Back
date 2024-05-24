package com.magicvault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.documents.Users;
import com.magicvault.jwt.response.AuthResponse;
import com.magicvault.jwt.service.JWTService;
import com.magicvault.repository.UsersRepository;
import com.magicvault.requests.LoginRequest;
import com.magicvault.requests.RegisterRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Load user details from UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generate JWT token
            String token = jwtService.getToken(userDetails);

            // Create response with the token
            AuthResponse authResponse = new AuthResponse(token);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Return unauthorized response for invalid credentials
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Check if user already exists
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
            }

            // Create a new user
            Users newUser = new Users();
            newUser.setUsername(request.getUsername());
            newUser.setPass(passwordEncoder.encode(request.getPassword())); // Encode password
            newUser.setEmail(request.getEmail());
            newUser.setType_rol("User"); // Assign a default role

            // Save the new user to the repository
            userRepository.save(newUser);

            // Load user details from UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generate JWT token
            String token = jwtService.getToken(userDetails);

            // Create response with the token
            AuthResponse authResponse = new AuthResponse(token);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Return internal server error response for registration error
            return new ResponseEntity<>("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
