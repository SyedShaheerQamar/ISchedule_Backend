package com.schedule.ISchedule.controller;

import com.schedule.ISchedule.dto.RoomCourseDTO;
import com.schedule.ISchedule.dto.RoomDTO;
import com.schedule.ISchedule.exceptions.ErrorResponse;
import com.schedule.ISchedule.model.Room;
import com.schedule.ISchedule.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
@CrossOrigin("http://localhost:4200")
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<?> saveRoom(@RequestBody RoomDTO roomDTO){

        Room room = this.roomService.saveRoom(roomDTO);

        if(room != null){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set room. Please try again.");
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRooms(){

        List<RoomCourseDTO> room = this.roomService.getAllRooms();

        if(!room.isEmpty()){
            return ResponseEntity.ok(room);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No rooms available.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id){
        this.roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
