package com.schedule.ISchedule.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface IJwtService {
    String extractUserName(String token);

    String generateTokenWithClaims(UserDetails userDetails, Map<String, Object> claims);

    Boolean validateToken(String token, UserDetails userDetails);

}
