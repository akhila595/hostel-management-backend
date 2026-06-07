package com.hostel_management.controller;

import com.hostel_management.dto.DashboardResponse;
import com.hostel_management.dto.StudentResponse;
import com.hostel_management.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardResponse getDashboardSummary() {
        return dashboardService.getDashboardSummary();
    }

    @GetMapping("/due-today")
    public List<StudentResponse> getDueTodayStudents() {
        return dashboardService.getDueTodayStudents();
    }

    @GetMapping("/overdue")
    public List<StudentResponse> getOverdueStudents() {
        return dashboardService.getOverdueStudents();
    }
}