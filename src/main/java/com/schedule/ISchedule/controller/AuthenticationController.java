package com.schedule.ISchedule.controller;


import com.schedule.ISchedule.dto.JwtAuthenticationResponse;
import com.schedule.ISchedule.dto.LoginRequest;
import com.schedule.ISchedule.dto.RegisterRequest;
import com.schedule.ISchedule.model.User;
import com.schedule.ISchedule.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User response = authenticationService.register(registerRequest);
        if(response != null){
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}


























