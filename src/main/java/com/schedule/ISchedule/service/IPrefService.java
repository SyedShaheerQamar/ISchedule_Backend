package com.schedule.ISchedule.service;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Preferences;

import java.util.List;

public interface IPrefService {

    Preferences savePref(CourseDTO courseDTO);
    List<CourseDTO> getAllPref();
    Course updateCourse(String id);
    void deletePref(String id);

}
