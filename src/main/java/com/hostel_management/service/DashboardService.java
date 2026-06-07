package com.hostel_management.service;

import com.hostel_management.dto.DashboardResponse;
import com.hostel_management.dto.StudentResponse;
import com.hostel_management.entity.Room;
import com.hostel_management.entity.Student;
import com.hostel_management.enums.StudentStatus;
import com.hostel_management.repository.RoomRepository;
import com.hostel_management.repository.StudentRepository;
import com.hostel_management.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final JwtUtils jwtUtils;
    private final FeePaymentService feePaymentService;

    public DashboardService(
            StudentRepository studentRepository,
            RoomRepository roomRepository,
            JwtUtils jwtUtils,
            FeePaymentService feePaymentService
    ) {

        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
        this.jwtUtils = jwtUtils;
        this.feePaymentService = feePaymentService;
    }

    // DASHBOARD SUMMARY
    public DashboardResponse getDashboardSummary() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        DashboardResponse response =
                new DashboardResponse();

        List<Student> students =
                studentRepository.findByCustomerId(customerId);

        List<Room> rooms =
                roomRepository.findByCustomerId(customerId);

        long activeStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus() == StudentStatus.ACTIVE)
                        .count();

        long vacatedStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus() == StudentStatus.VACATED)
                        .count();

        long occupiedBeds =
                rooms.stream()
                        .mapToLong(Room::getOccupiedBeds)
                        .sum();

        long totalBeds =
                rooms.stream()
                        .mapToLong(Room::getTotalBeds)
                        .sum();

        long dueToday =
                students.stream()
                        .filter(s ->
                                s.getStatus() == StudentStatus.ACTIVE)
                        .filter(s ->
                                s.getNextDueDate()
                                        .equals(LocalDate.now()))
                        .count();

        long overdue =
                students.stream()
                        .filter(s ->
                                s.getStatus() == StudentStatus.ACTIVE)
                        .filter(s ->
                                s.getNextDueDate()
                                        .isBefore(LocalDate.now()))
                        .count();

        double pendingAmount =
                students.stream()
                        .filter(s ->
                                s.getStatus() == StudentStatus.ACTIVE)
                        .filter(s ->
                                !s.getNextDueDate()
                                        .isAfter(LocalDate.now()))
                        .mapToDouble(Student::getFeeAmount)
                        .sum();

        response.setTotalStudents(
                (long) students.size()
        );

        response.setActiveStudents(
                activeStudents
        );

        response.setVacatedStudents(
                vacatedStudents
        );

        response.setTotalRooms(
                (long) rooms.size()
        );

        response.setOccupiedBeds(
                occupiedBeds
        );

        response.setAvailableBeds(
                totalBeds - occupiedBeds
        );

        response.setDueTodayCount(
                dueToday
        );

        response.setOverdueCount(
                overdue
        );

        response.setPendingAmount(
                pendingAmount
        );

        response.setCollectedThisMonth(
                feePaymentService.getCollectedThisMonth()
        );

        return response;
    }

    // DUE TODAY
    public List<StudentResponse> getDueTodayStudents() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        return studentRepository
                .findByCustomerId(customerId)
                .stream()
                .filter(s ->
                        s.getStatus() == StudentStatus.ACTIVE)
                .filter(s ->
                        s.getNextDueDate()
                                .equals(LocalDate.now()))
                .map(this::mapToResponse)
                .toList();
    }

    // OVERDUE
    public List<StudentResponse> getOverdueStudents() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        return studentRepository
                .findByCustomerId(customerId)
                .stream()
                .filter(s ->
                        s.getStatus() == StudentStatus.ACTIVE)
                .filter(s ->
                        s.getNextDueDate()
                                .isBefore(LocalDate.now()))
                .map(this::mapToResponse)
                .toList();
    }

    // MAP RESPONSE
    private StudentResponse mapToResponse(
            Student student
    ) {

        StudentResponse response =
                new StudentResponse();

        response.setId(
                student.getId()
        );

        response.setFullName(
                student.getFullName()
        );

        response.setPhoneNumber(
                student.getPhoneNumber()
        );

        response.setFeeAmount(
                student.getFeeAmount()
        );

        response.setJoiningDate(
                student.getJoiningDate()
        );

        response.setNextDueDate(
                student.getNextDueDate()
        );

        response.setRoomNumber(
                student.getRoom().getRoomNumber()
        );

        response.setBedNumber(
                student.getBed().getBedNumber()
        );

        response.setStatus(
                student.getStatus().name()
        );

        return response;
    }
}