package com.schedule.ISchedule.service;

import com.schedule.ISchedule.dto.RoomCourseDTO;
import com.schedule.ISchedule.dto.RoomDTO;
import com.schedule.ISchedule.model.Room;

import java.util.List;

public interface IRoomService {

    Room saveRoom(RoomDTO roomDTO);
    List<RoomCourseDTO> getAllRooms();

}
