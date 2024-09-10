package com.schedule.ISchedule.controller;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.exceptions.ErrorResponse;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
@CrossOrigin("http://localhost:4200")
public class CourseController {

    private final ICourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO) {
        Course course = this.courseService.saveCourse(courseDTO);

        if (course != null) {
            return ResponseEntity.ok().build();
        }

        throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
    }

    @GetMapping("")
    public ResponseEntity<?> getCourseByRoom(){
        List<CourseDTO> courses = this.courseService.getAllCourses();

        if (courses.isEmpty()) {
            return ResponseEntity.ok(Collections.emptySet());
        }

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/timetable")
    public ResponseEntity<?> getTimetable(){
        List<CourseDTO> courses = this.courseService.getCoursesByDay();

        if (courses.isEmpty()) {
            return ResponseEntity.ok(Collections.emptySet());
        }

        return ResponseEntity.ok().body(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id){
        this.courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

}
