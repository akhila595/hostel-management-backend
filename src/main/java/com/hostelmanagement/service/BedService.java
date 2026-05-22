package com.hostelmanagement.service;

import com.hostelmanagement.entity.Bed;
import com.hostelmanagement.repository.BedRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {

    private final BedRepository bedRepository;

    public BedService(
            BedRepository bedRepository
    ) {

        this.bedRepository =
                bedRepository;
    }

    public List<Bed> getBedsByRoom(
            Long roomId
    ) {

        return bedRepository
                .findByRoomId(roomId);
    }
}