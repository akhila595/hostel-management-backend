package com.hostel_management.service;

import com.hostel_management.entity.Bed;
import com.hostel_management.repository.BedRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {

	private final BedRepository bedRepository;

	public BedService(BedRepository bedRepository) {

		this.bedRepository = bedRepository;
	}

	public List<Bed> getBedsByRoom(Long roomId) {

		return bedRepository.findByRoomId(roomId);
	}
}