package com.hostelmanagement.controller;

import com.hostelmanagement.dto.RoomRequest;
import com.hostelmanagement.dto.RoomResponse;
import com.hostelmanagement.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public RoomResponse createRoom(@RequestBody RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(
            @PathVariable Long id,
            @RequestBody RoomRequest request
    ) {
        return roomService.updateRoom(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}