package com.hostel_management.dto;

public class DashboardResponse {

    private Long totalStudents;
    private Long activeStudents;
    private Long vacatedStudents;
    private Long totalRooms;
    private Long occupiedBeds;
    private Long availableBeds;
    private Long dueTodayCount;
    private Long overdueCount;
    private Double pendingAmount;
    private Double collectedThisMonth;

    public DashboardResponse() {
    }
    
    public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Double getCollectedThisMonth() {
		return collectedThisMonth;
	}

	public void setCollectedThisMonth(Double collectedThisMonth) {
		this.collectedThisMonth = collectedThisMonth;
	}

	public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(Long activeStudents) {
        this.activeStudents = activeStudents;
    }

    public Long getVacatedStudents() {
        return vacatedStudents;
    }

    public void setVacatedStudents(Long vacatedStudents) {
        this.vacatedStudents = vacatedStudents;
    }

    public Long getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Long totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Long getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(Long occupiedBeds) {
        this.occupiedBeds = occupiedBeds;
    }

    public Long getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(Long availableBeds) {
        this.availableBeds = availableBeds;
    }

    public Long getDueTodayCount() {
        return dueTodayCount;
    }

    public void setDueTodayCount(Long dueTodayCount) {
        this.dueTodayCount = dueTodayCount;
    }

    public Long getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(Long overdueCount) {
        this.overdueCount = overdueCount;
    }
}