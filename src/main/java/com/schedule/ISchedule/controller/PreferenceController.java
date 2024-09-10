package com.schedule.ISchedule.controller;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.exceptions.ErrorResponse;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Preferences;
import com.schedule.ISchedule.service.IPrefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pref")
@CrossOrigin("http://localhost:4200")
public class PreferenceController {

    private final IPrefService prefService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO) {
        Preferences preferences = this.prefService.savePref(courseDTO);

        if (preferences != null) {
            return ResponseEntity.ok().build();
        }

        throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPrefs(){
        List<CourseDTO> courses = this.prefService.getAllPref();

        if (courses.isEmpty()) {
            return ResponseEntity.ok(Collections.emptySet());
        }

        return ResponseEntity.ok().body(courses);
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id){
        Course courses = this.prefService.updateCourse(id);

        if (courses == null) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
        }

        return ResponseEntity.ok().body(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePred(@PathVariable String id){
        this.prefService.deletePref(id);
        return ResponseEntity.ok().build();
    }

}
