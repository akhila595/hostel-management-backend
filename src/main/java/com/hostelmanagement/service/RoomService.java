package com.hostelmanagement.service;

import com.hostelmanagement.dto.RoomRequest;
import com.hostelmanagement.dto.RoomResponse;
import com.hostelmanagement.entity.Bed;
import com.hostelmanagement.entity.Room;
import com.hostelmanagement.enums.BedStatus;
import com.hostelmanagement.repository.BedRepository;
import com.hostelmanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public RoomService(RoomRepository roomRepository,
                       BedRepository bedRepository) {

        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    // CREATE ROOM
    public RoomResponse createRoom(RoomRequest request) {

        // CHECK ROOM EXISTS
        if (roomRepository.existsByRoomNumber(request.getRoomNumber())) {
            throw new RuntimeException("Room already exists");
        }

        // CREATE ROOM
        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setTotalBeds(request.getTotalBeds());
        room.setOccupiedBeds(0);
        room.setRoomType(request.getRoomType());
        room.setFloorNumber(request.getFloorNumber());
        room.setRemarks(request.getRemarks());

        // SAVE ROOM
        Room savedRoom = roomRepository.save(room);

        // CREATE BEDS
        List<Bed> beds = new ArrayList<>();

        for (int i = 1; i <= request.getTotalBeds(); i++) {

            Bed bed = new Bed();

            bed.setBedNumber("Bed-" + i);
            bed.setStatus(BedStatus.AVAILABLE);
            bed.setRoom(savedRoom);

            beds.add(bed);
        }

        // SAVE ALL BEDS
        bedRepository.saveAll(beds);

        // RETURN RESPONSE
        return mapToResponse(savedRoom);
    }

    // GET ALL ROOMS
    public List<RoomResponse> getAllRooms() {

        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ROOM BY ID
    public RoomResponse getRoomById(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Room not found"));

        return mapToResponse(room);
    }

    // UPDATE ROOM
    public RoomResponse updateRoom(Long id,
                                   RoomRequest request) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Room not found"));

        room.setRoomType(request.getRoomType());
        room.setFloorNumber(request.getFloorNumber());
        room.setRemarks(request.getRemarks());

        Room updatedRoom = roomRepository.save(room);

        return mapToResponse(updatedRoom);
    }

    // DELETE ROOM
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }

    // MAP ROOM ENTITY TO RESPONSE DTO
    private RoomResponse mapToResponse(Room room) {

        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setTotalBeds(room.getTotalBeds());
        response.setOccupiedBeds(room.getOccupiedBeds());

        response.setAvailableBeds(
                room.getTotalBeds() - room.getOccupiedBeds()
        );

        response.setRoomType(room.getRoomType());
        response.setFloorNumber(room.getFloorNumber());
        response.setRemarks(room.getRemarks());

        return response;
    }
}