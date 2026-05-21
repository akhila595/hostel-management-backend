package com.hostelmanagement.entity;

import com.hostelmanagement.enums.BedStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "beds")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bedNumber;

    @Enumerated(EnumType.STRING)
    private BedStatus status;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = BedStatus.AVAILABLE;
        }
    }

	public Bed() {
		super();
	}

	public Bed(String bedNumber, BedStatus status, Room room) {
		super();
		this.bedNumber = bedNumber;
		this.status = status;
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public BedStatus getStatus() {
		return status;
	}

	public void setStatus(BedStatus status) {
		this.status = status;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
    
    
}