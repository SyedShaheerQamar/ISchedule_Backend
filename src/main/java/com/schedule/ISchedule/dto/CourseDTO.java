package com.schedule.ISchedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {

    private Long id;
    private String courseName;
    private String roomNumber;
    private String startTime;
    private String endTime;
    private List<String> days;

}
