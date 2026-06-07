package com.hostel_management.service;

import com.hostel_management.dto.RoomRequest;
import com.hostel_management.dto.RoomResponse;
import com.hostel_management.entity.Bed;
import com.hostel_management.entity.Customer;
import com.hostel_management.entity.Room;
import com.hostel_management.enums.BedStatus;
import com.hostel_management.repository.BedRepository;
import com.hostel_management.repository.CustomerRepository;
import com.hostel_management.repository.RoomRepository;
import com.hostel_management.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;

    public RoomService(RoomRepository roomRepository,
                       BedRepository bedRepository,
                       JwtUtils jwtUtils,
                       CustomerRepository customerRepository) {

        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
        this.jwtUtils = jwtUtils;
        this.customerRepository = customerRepository;
    }

    // CREATE ROOM
    public RoomResponse createRoom(RoomRequest request) {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new RuntimeException("Customer not found"));

        if (roomRepository.existsByRoomNumber(
                request.getRoomNumber()
        )) {

            throw new RuntimeException(
                    "Room already exists"
            );
        }

        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setTotalBeds(request.getTotalBeds());
        room.setOccupiedBeds(0);
        room.setRoomType(request.getRoomType());
        room.setFloorNumber(request.getFloorNumber());
        room.setRemarks(request.getRemarks());

        room.setCustomer(customer);

        Room savedRoom =
                roomRepository.save(room);

        List<Bed> beds = new ArrayList<>();

        for (int i = 1; i <= request.getTotalBeds(); i++) {

            Bed bed = new Bed();

            bed.setBedNumber("Bed-" + i);
            bed.setStatus(BedStatus.AVAILABLE);
            bed.setRoom(savedRoom);

            beds.add(bed);
        }

        bedRepository.saveAll(beds);

        return mapToResponse(savedRoom);
    }

    // GET ALL ROOMS
    public List<RoomResponse> getAllRooms() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        return roomRepository
                .findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ROOM BY ID
    public RoomResponse getRoomById(Long id) {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        Room room =
                roomRepository
                        .findByIdAndCustomerId(
                                id,
                                customerId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"
                                ));

        return mapToResponse(room);
    }

    // UPDATE ROOM
    public RoomResponse updateRoom(
            Long id,
            RoomRequest request
    ) {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        Room room =
                roomRepository
                        .findByIdAndCustomerId(
                                id,
                                customerId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"
                                ));

        room.setRoomType(request.getRoomType());
        room.setFloorNumber(request.getFloorNumber());
        room.setRemarks(request.getRemarks());

        Room updatedRoom =
                roomRepository.save(room);

        return mapToResponse(updatedRoom);
    }

    // DELETE ROOM
    public void deleteRoom(Long id) {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        Room room =
                roomRepository
                        .findByIdAndCustomerId(
                                id,
                                customerId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"
                                ));

        roomRepository.delete(room);
    }

    // MAP RESPONSE
    private RoomResponse mapToResponse(Room room) {

        RoomResponse response =
                new RoomResponse();

        response.setId(room.getId());

        response.setRoomNumber(
                room.getRoomNumber()
        );

        response.setTotalBeds(
                room.getTotalBeds()
        );

        response.setOccupiedBeds(
                room.getOccupiedBeds()
        );

        response.setAvailableBeds(
                room.getTotalBeds()
                        - room.getOccupiedBeds()
        );

        response.setRoomType(
                room.getRoomType()
        );

        response.setFloorNumber(
                room.getFloorNumber()
        );

        response.setRemarks(
                room.getRemarks()
        );

        return response;
    }
}