package com.schedule.ISchedule.repository;

import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Preferences;
import com.schedule.ISchedule.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IPrefRepository extends JpaRepository<Preferences, Long> {

    Optional<Preferences> findPreferencesByCourseName(String courseName);


}
