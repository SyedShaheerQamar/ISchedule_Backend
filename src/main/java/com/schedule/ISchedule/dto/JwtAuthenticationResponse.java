package com.schedule.ISchedule.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String role;
}
