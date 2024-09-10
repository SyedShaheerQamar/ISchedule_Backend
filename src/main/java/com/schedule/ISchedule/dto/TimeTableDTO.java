package com.schedule.ISchedule.dto;

import com.schedule.ISchedule.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeTableDTO {

    private String roomName;
    private List<CourseDTO> course;

}
