package com.schedule.ISchedule.service;

import com.schedule.ISchedule.model.Course;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface IUserService {
    UserDetailsService userDetailsService();

    Course saveUserCourse(String email, String courseName);
}
