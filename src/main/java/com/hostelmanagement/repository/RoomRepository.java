package com.hostelmanagement.repository;

import com.hostelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository
        extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(String roomNumber);

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByCustomerId(Long customerId);

    Optional<Room> findByIdAndCustomerId(
            Long id,
            Long customerId
    );
}