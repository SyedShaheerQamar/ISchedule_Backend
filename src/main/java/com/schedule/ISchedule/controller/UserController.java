package com.schedule.ISchedule.controller;

import com.schedule.ISchedule.exceptions.ErrorResponse;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private final IUserService userService;

    @PostMapping("/saveCourse/{email}/{courseName}")
    public ResponseEntity<?> saveCourseForStudent(@PathVariable String email, @PathVariable String courseName){

        Course course = this.userService.saveUserCourse(email, courseName);
        if(course != null){
            return ResponseEntity.ok().build();
        }

        throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
    }
}
