package com.magicvault.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.magicvault.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @Autowired
    private UsersRepository userRepository;
    
    // Bean definition for RestTemplate, creates a RestTemplate instance
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Bean definition for AuthenticationManager, retrieves AuthenticationManager from configuration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean definition for AuthenticationProvider, configures an AuthenticationProvider instance
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Creates a DaoAuthenticationProvider instance
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        // Sets UserDetailsService for the authentication provider
        authenticationProvider.setUserDetailsService(userDetailService());
        // Sets PasswordEncoder for the authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Bean definition for PasswordEncoder, creates a BCryptPasswordEncoder instance
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean definition for UserDetailsService, retrieves UserDetails from UserRepository based on username
    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
