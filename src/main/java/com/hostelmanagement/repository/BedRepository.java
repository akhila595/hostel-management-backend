package com.hostelmanagement.repository;

import com.hostelmanagement.entity.Bed;
import com.hostelmanagement.enums.BedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findByRoomId(Long roomId);

    List<Bed> findByRoomIdAndStatus(Long roomId, BedStatus status);
}