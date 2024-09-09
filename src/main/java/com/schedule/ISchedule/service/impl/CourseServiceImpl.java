package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.dto.CourseDTO;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Room;
import com.schedule.ISchedule.repository.ICourseRepository;
import com.schedule.ISchedule.repository.IRoomRepository;
import com.schedule.ISchedule.service.ICourseService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository courseRepository;
    private final IRoomRepository roomRepository;

    public CourseServiceImpl(ICourseRepository courseRepository, IRoomRepository roomRepository) {
        this.courseRepository = courseRepository;
        this.roomRepository = roomRepository;
    }
    @Override
    public Course saveCourse(CourseDTO courseDTO) {
        Optional<Room> roomOptional = this.roomRepository.findByRoomNumber(courseDTO.getRoomNumber());

        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            Set<Course> courseSet = this.courseRepository.findCourseByRoom(room);

            if (checkToVerify(courseSet, courseDTO)) {

                // Convert list of days to a comma-separated string
                String days = String.join(",", courseDTO.getDays());

                try {
                    LocalTime startTime = parseTime(courseDTO.getStartTime());
                    LocalTime endTime = parseTime(courseDTO.getEndTime());

                    // Build the new Course object
                    Course course = new Course();
                    course.setCourseName(courseDTO.getCourseName());
                    course.setStartTime(startTime);
                    course.setEndTime(endTime);
                    course.setDays(days);
                    course.setRoom(room);

                    return this.courseRepository.save(course);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid time format: " + e.getMessage());
                }
            }
        }

        // Return null if room is not found or validation fails
        return null;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();

        for(Course course : courses){
            CourseDTO courseDTO = CourseDTO.builder()
                    .id(course.getId())
                    .courseName(course.getCourseName())
                    .startTime(String.valueOf(course.getStartTime()))
                    .endTime(String.valueOf(course.getEndTime()))
                    .roomNumber(course.getRoom().getRoomNumber())
                    .days(Collections.singletonList(course.getDays()))
                    .build();

            courseDTOS.add(courseDTO);
        }

        return courseDTOS;
    }

    @Override
    public void deleteCourse(String id) {
        this.courseRepository.deleteById(Long.valueOf(id));
    }

    private Boolean checkToVerify(Set<Course> courseSet, CourseDTO courseDTO) {

        for(Course course : courseSet){
            Optional<Course> optionalCourse = this.courseRepository.findCourseByCourseName(course.getCourseName());

            if(optionalCourse.isPresent()){
                if(courseDTO.getRoomNumber().equalsIgnoreCase(optionalCourse.get().getRoom().getRoomNumber())){
                    List<String> daysList = Arrays.asList(optionalCourse.get().getDays().split(","));

                    for(String day : courseDTO.getDays()){
                        if(daysList.contains(day)){
                            return isTimeConflict(courseDTO, optionalCourse.get());
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isTimeConflict(CourseDTO courseDTO, Course existingCourse) {
        // Parse new course times
        LocalTime newStartTime = parseTime(courseDTO.getStartTime());
        LocalTime newEndTime = parseTime(courseDTO.getEndTime());

        // Existing course times
        LocalTime existingStartTime = existingCourse.getStartTime();
        LocalTime existingEndTime = existingCourse.getEndTime();

        if (newStartTime.isBefore(existingEndTime)) {

            if (newEndTime.isAfter(existingStartTime)) {

                if (newStartTime.equals(existingEndTime) || newEndTime.equals(existingStartTime)) {
                    // Intervals are consecutive
                    return false;
                } else {
                    // Intervals overlap
                    return true;
                }

            } else {
                // New interval ends before existing interval starts (no overlap)
                return false;
            }
        } else {
            // New interval starts after existing interval ends (no overlap)
            return false;
        }

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
