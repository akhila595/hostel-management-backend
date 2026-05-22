package com.hostelmanagement.service;

import com.hostelmanagement.dto.DashboardResponse;
import com.hostelmanagement.dto.StudentResponse;
import com.hostelmanagement.entity.Room;
import com.hostelmanagement.entity.Student;
import com.hostelmanagement.enums.StudentStatus;
import com.hostelmanagement.repository.RoomRepository;
import com.hostelmanagement.repository.StudentRepository;
import com.hostelmanagement.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final JwtUtils jwtUtils;

    public DashboardService(
            StudentRepository studentRepository,
            RoomRepository roomRepository,
            JwtUtils jwtUtils
    ) {

        this.studentRepository =
                studentRepository;

        this.roomRepository =
                roomRepository;

        this.jwtUtils = jwtUtils;
    }

    // DASHBOARD SUMMARY
    public DashboardResponse getDashboardSummary() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        DashboardResponse response =
                new DashboardResponse();

        List<Student> students =
                studentRepository
                        .findByCustomerId(customerId);

        List<Room> rooms =
                roomRepository
                        .findByCustomerId(customerId);

        long activeStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus()
                                        == StudentStatus.ACTIVE)
                        .count();

        long vacatedStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus()
                                        == StudentStatus.VACATED)
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
                                s.getNextDueDate()
                                        .equals(LocalDate.now()))
                        .count();

        long overdue =
                students.stream()
                        .filter(s ->
                                s.getNextDueDate()
                                        .isBefore(LocalDate.now()))
                        .count();

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

        return response;
    }

    // DUE TODAY
    public List<StudentResponse>
    getDueTodayStudents() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        return studentRepository
                .findByCustomerId(customerId)
                .stream()
                .filter(s ->
                        s.getNextDueDate()
                                .equals(LocalDate.now()))
                .map(this::mapToResponse)
                .toList();
    }

    // OVERDUE
    public List<StudentResponse>
    getOverdueStudents() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        return studentRepository
                .findByCustomerId(customerId)
                .stream()
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

        response.setId(student.getId());

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
                student.getRoom()
                        .getRoomNumber()
        );

        response.setBedNumber(
                student.getBed()
                        .getBedNumber()
        );

        response.setStatus(
                student.getStatus().name()
        );

        return response;
    }
}