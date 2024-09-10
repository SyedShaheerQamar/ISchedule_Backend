package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.User;
import com.schedule.ISchedule.repository.ICourseRepository;
import com.schedule.ISchedule.repository.IUserRepository;
import com.schedule.ISchedule.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final ICourseRepository courseRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

//    @Transactional
    @Override
    public Course saveUserCourse(String email, String courseName) {
        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Course> courseOptional = this.courseRepository.findCourseByCourseName(courseName);

            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();

                if (!user.getCourses().contains(course)) {
                    user.getCourses().add(course);
                }

                userRepository.save(user);

                return course;
            }
        }

        return null;
    }


}
