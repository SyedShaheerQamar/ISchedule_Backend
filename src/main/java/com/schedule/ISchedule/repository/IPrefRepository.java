package com.schedule.ISchedule.repository;

import com.schedule.ISchedule.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrefRepository extends JpaRepository<Preferences, Long> {

    List<Preferences> findPreferencesByCourseName(String courseName);


}
