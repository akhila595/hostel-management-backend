package com.hostelmanagement.controller;

import com.hostelmanagement.dto.DashboardResponse;
import com.hostelmanagement.dto.StudentResponse;
import com.hostelmanagement.service.DashboardService;
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