package com.schedule.ISchedule.service;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.dto.RoomCourseDTO;
import com.schedule.ISchedule.dto.TimeTableDTO;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Room;

import java.util.List;
import java.util.Set;

public interface ICourseService {

    Course saveCourse(CourseDTO courseDTO);
    List<CourseDTO> getAllCourses();
    void deleteCourse(String id);
    List<CourseDTO> getCoursesByDay();

}
