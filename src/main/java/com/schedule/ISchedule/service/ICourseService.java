package com.schedule.ISchedule.service;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.model.Course;

import java.util.List;

public interface ICourseService {

    Course saveCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourses();
    void deleteCourse(String id);
    List<CourseDTO> getCoursesByDay();

}
