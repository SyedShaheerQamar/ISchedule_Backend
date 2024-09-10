package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Preferences;
import com.schedule.ISchedule.model.Room;
import com.schedule.ISchedule.repository.ICourseRepository;
import com.schedule.ISchedule.repository.IPrefRepository;
import com.schedule.ISchedule.repository.IRoomRepository;
import com.schedule.ISchedule.service.IPrefService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PrefServiceImpl implements IPrefService {

    private final IPrefRepository prefRepository;
    private final IRoomRepository roomRepository;
    private final ICourseRepository courseRepository;

    public PrefServiceImpl(IPrefRepository prefRepository, IRoomRepository roomRepository, ICourseRepository courseRepository) {
        this.prefRepository = prefRepository;
        this.roomRepository = roomRepository;
        this.courseRepository = courseRepository;
    }
    @Override
    public Preferences savePref(CourseDTO courseDTO) {
        Optional<Room> roomOptional = this.roomRepository.findByRoomNumber(courseDTO.getRoomNumber());

        if (roomOptional.isPresent()) {
            String days = String.join(",", courseDTO.getDays());

            try {
                LocalTime startTime = parseTime(courseDTO.getStartTime());
                LocalTime endTime = parseTime(courseDTO.getEndTime());

                // Build the new Course object
                Preferences preferences = Preferences.builder()
                        .courseName(courseDTO.getCourseName())
                        .roomName(courseDTO.getRoomNumber())
                        .days(days)
                        .endTime(endTime)
                        .startTime(startTime)
                        .build();

                return this.prefRepository.save(preferences);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid time format: " + e.getMessage());
            }

        }

        return null;
    }

    @Override
    public List<CourseDTO> getAllPref() {
        List<Preferences> preferences = this.prefRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();

        for(Preferences preference : preferences){
            CourseDTO courseDTO = CourseDTO.builder()
                    .id(preference.getId())
                    .courseName(preference.getCourseName())
                    .startTime(String.valueOf(preference.getStartTime()))
                    .endTime(String.valueOf(preference.getEndTime()))
                    .roomNumber(preference.getRoomName())
                    .days(Collections.singletonList(preference.getDays()))
                    .build();

            courseDTOS.add(courseDTO);
        }

        return courseDTOS;
    }

    @Override
    public Course updateCourse(String id) {
        Optional<Preferences> preferences = this.prefRepository.findById(Long.valueOf(id));

        if(preferences.isPresent()){
            Optional<Course> course = this.courseRepository.findCourseByCourseName(preferences.get().getCourseName());

            if(course.isPresent()){
                Optional<Room> roomOptional = this.roomRepository.findByRoomNumber(preferences.get().getRoomName());

                Course savedCourse = course.get();

                savedCourse.setRoom(roomOptional.get());
                savedCourse.setEndTime(preferences.get().getEndTime());
                savedCourse.setStartTime(preferences.get().getStartTime());
                savedCourse.setDays(preferences.get().getDays());

                return this.courseRepository.save(savedCourse);
            }
        }

        return null;
    }

    @Override
    public void deletePref(String id) {
        this.prefRepository.deleteById(Long.valueOf(id));
    }

    private LocalTime parseTime(String timeStr) {
        int timeValue = Integer.parseInt(timeStr);

        // If the time is <= 12, assume it's AM for now
        if (timeValue <= 12) {
            return LocalTime.of(timeValue, 0);  // Assuming AM
        } else {
            // If the time is greater than 12, it's in 24-hour format (like 13 or 22)
            return LocalTime.of(timeValue, 0);  // 24-hour format
        }
    }
}
