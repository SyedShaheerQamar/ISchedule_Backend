package com.schedule.ISchedule.repository;

import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByCourseName(String courseName);

    Set<Course> findCourseByRoom(Room room);

}
