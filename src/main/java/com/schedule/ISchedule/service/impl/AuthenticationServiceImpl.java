package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.dto.JwtAuthenticationResponse;
import com.schedule.ISchedule.dto.LoginRequest;
import com.schedule.ISchedule.dto.RegisterRequest;
import com.schedule.ISchedule.exceptions.ErrorResponse;
import com.schedule.ISchedule.model.RoleEnum;
import com.schedule.ISchedule.model.User;
import com.schedule.ISchedule.repository.IUserRepository;
import com.schedule.ISchedule.service.IAuthenticationService;
import com.schedule.ISchedule.service.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    public User register(RegisterRequest registerRequest) {

        Optional<User> user = this.userRepository.findByEmail(registerRequest.getEmail());

        if(user.isEmpty()){
            RoleEnum role = Arrays.stream(RoleEnum.values())
                    .filter(r -> r.toString().equalsIgnoreCase(registerRequest.getRole()))
                    .findFirst()
                    .orElseThrow(() -> new ErrorResponse(HttpStatus.BAD_REQUEST,"Invalid role: " + registerRequest.getRole()));

            User savedUser = User.builder()
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(role)
                    .build();

            return userRepository.save(savedUser);
        }

        return null;
    }

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        // Authenticate the user using the authentication manager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword())
        );

        // Retrieve user from the database
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Prepare claims for the JWT token (e.g., email and role)
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail()); // Add email to the claims
        claims.put("role", user.getRole().name()); // Add role to the claims

        // Generate the JWT token with the claims
        var jwt = jwtService.generateTokenWithClaims(user, claims);

        // Prepare the response with token and role
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRole(user.getRole().name());

        return jwtAuthenticationResponse;
    }


}
