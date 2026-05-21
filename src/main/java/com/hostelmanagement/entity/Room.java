package com.hostelmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rooms")

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomNumber;

    private Integer totalBeds;

    private Integer occupiedBeds;

    private String roomType;

    private Integer floorNumber;

    private String remarks;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Bed> beds;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.occupiedBeds == null) {
            this.occupiedBeds = 0;
        }
    }

	public Room(String roomNumber, Integer totalBeds, Integer occupiedBeds, String roomType,
			Integer floorNumber, String remarks, LocalDateTime createdAt, List<Bed> beds) {
		super();
		this.roomNumber = roomNumber;
		this.totalBeds = totalBeds;
		this.occupiedBeds = occupiedBeds;
		this.roomType = roomType;
		this.floorNumber = floorNumber;
		this.remarks = remarks;
		this.createdAt = createdAt;
		this.beds = beds;
	}

	public Room() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Integer getTotalBeds() {
		return totalBeds;
	}

	public void setTotalBeds(Integer totalBeds) {
		this.totalBeds = totalBeds;
	}

	public Integer getOccupiedBeds() {
		return occupiedBeds;
	}

	public void setOccupiedBeds(Integer occupiedBeds) {
		this.occupiedBeds = occupiedBeds;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Bed> getBeds() {
		return beds;
	}

	public void setBeds(List<Bed> beds) {
		this.beds = beds;
	}
    
}