package com.hostel_management.controller;

import com.hostel_management.dto.StudentRequest;
import com.hostel_management.dto.StudentResponse;
import com.hostel_management.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin("*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentResponse createStudent(
            @RequestBody StudentRequest request
    ) {
        return studentService.createStudent(request);
    }

    @GetMapping
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(
            @PathVariable Long id
    ) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequest request
    ) {
        return studentService.updateStudent(id, request);
    }

    @PutMapping("/vacate/{id}")
    public String vacateStudent(
            @PathVariable Long id
    ) {
        studentService.vacateStudent(id);

        return "Student vacated successfully";
    }
}