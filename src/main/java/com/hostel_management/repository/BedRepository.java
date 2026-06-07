package com.hostel_management.repository;

import com.hostel_management.entity.Bed;
import com.hostel_management.enums.BedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BedRepository
        extends JpaRepository<Bed, Long> {

    List<Bed> findByRoomId(Long roomId);

    List<Bed> findByRoomIdAndStatus(
            Long roomId,
            BedStatus status
    );

    Optional<Bed> findById(Long id);
}