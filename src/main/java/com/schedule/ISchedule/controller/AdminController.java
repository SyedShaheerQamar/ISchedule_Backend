package com.schedule.ISchedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:4200")
public class AdminController {
    @GetMapping()
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("HI ADMIN");
    }
}
