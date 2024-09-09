package com.schedule.ISchedule.service.impl;

import com.schedule.ISchedule.dto.RoomCourseDTO;
import com.schedule.ISchedule.dto.RoomDTO;
import com.schedule.ISchedule.model.Course;
import com.schedule.ISchedule.model.Room;
import com.schedule.ISchedule.repository.ICourseRepository;
import com.schedule.ISchedule.repository.IRoomRepository;
import com.schedule.ISchedule.service.IRoomService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    private final IRoomRepository roomRepository;
    private final ICourseRepository courseRepository;

    public RoomServiceImpl(IRoomRepository roomRepository, ICourseRepository courseRepository) {
        this.roomRepository = roomRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Room saveRoom(RoomDTO roomDTO) {

        Optional<Room> room = this.roomRepository.findByRoomNumber(roomDTO.getRoomName());

        if(room.isEmpty()){
            Room saveRoom = Room.builder()
                    .roomNumber(roomDTO.getRoomName())
                    .build();

            return this.roomRepository.save(saveRoom);
        }

        return null;
    }

    @Override
    public List<RoomCourseDTO> getAllRooms() {
        List<Room> rooms = this.roomRepository.findAll();
        List<RoomCourseDTO> roomCourseDTOList = new ArrayList<>();

        for (Room room : rooms) {
            Set<Course> courses = this.courseRepository.findCourseByRoom(room);
            if (!courses.isEmpty()) {
                String daysList = courses.stream()
                        .map(Course::getCourseName)
                        .filter(courseName -> courseName != null && !courseName.trim().isEmpty())
                        .collect(Collectors.joining(", "));


                RoomCourseDTO roomCourseDTO = RoomCourseDTO.builder()
                        .id(room.getId())
                        .roomName(room.getRoomNumber())
                        .course(daysList)
                        .build();

                roomCourseDTOList.add(roomCourseDTO);
            }
            else {
                RoomCourseDTO roomCourseDTO = RoomCourseDTO.builder()
                        .id(room.getId())
                        .roomName(room.getRoomNumber())
                        .build();

                roomCourseDTOList.add(roomCourseDTO);
            }
        }

        return roomCourseDTOList;
    }
}
