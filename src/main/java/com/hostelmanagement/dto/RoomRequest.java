package com.hostelmanagement.dto;

public class RoomRequest {

    private String roomNumber;

    private Integer totalBeds;

    private String roomType;

    private Integer floorNumber;

    private String remarks;

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
    
    
}