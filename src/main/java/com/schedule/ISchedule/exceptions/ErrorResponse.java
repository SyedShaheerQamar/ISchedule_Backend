package com.schedule.ISchedule.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse extends RuntimeException {

    private final HttpStatus status;
    private final String message;


}