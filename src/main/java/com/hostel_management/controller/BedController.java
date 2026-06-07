package com.hostel_management.controller;

import com.hostel_management.entity.Bed;
import com.hostel_management.service.BedService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beds")
@CrossOrigin
public class BedController {

	private final BedService bedService;

	public BedController(BedService bedService) {

		this.bedService = bedService;
	}

	@GetMapping("/room/{roomId}")
	public ResponseEntity<List<Bed>> getBedsByRoom(@PathVariable Long roomId) {

		return ResponseEntity.ok(bedService.getBedsByRoom(roomId));
	}
}