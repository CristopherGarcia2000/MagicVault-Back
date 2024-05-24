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
@CrossOrigin(origins = "*",allowedHeaders="*")
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Autenticar al usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Cargar detalles del usuario desde el UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generar el token JWT
            String token = jwtService.getToken(userDetails);

            // Crear la respuesta con el token
            AuthResponse authResponse = new AuthResponse(token);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Verificar si el usuario ya existe
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return new ResponseEntity<>("El nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
            }

            // Crear un nuevo usuario
            Users newUser = new Users();
            newUser.setUsername(request.getUsername());
            newUser.setPass(passwordEncoder.encode(request.getPassword()));
            newUser.setEmail(request.getEmail());
            newUser.setType_rol("User"); // Asignar un rol por defecto

            // Guardar el nuevo usuario en el repositorio
            userRepository.save(newUser);

            // Cargar detalles del usuario desde el UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generar el token JWT
            String token = jwtService.getToken(userDetails);

            // Crear la respuesta con el token
            AuthResponse authResponse = new AuthResponse(token);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

