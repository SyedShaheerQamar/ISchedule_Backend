package com.schedule.ISchedule.service;


import com.schedule.ISchedule.dto.JwtAuthenticationResponse;
import com.schedule.ISchedule.dto.LoginRequest;
import com.schedule.ISchedule.dto.RegisterRequest;
import com.schedule.ISchedule.model.User;


public interface IAuthenticationService {
    User register(RegisterRequest registerRequest) ;
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}
