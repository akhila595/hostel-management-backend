package com.hostelmanagement.service;

import com.hostelmanagement.dto.DashboardResponse;
import com.hostelmanagement.dto.StudentResponse;
import com.hostelmanagement.entity.Room;
import com.hostelmanagement.entity.Student;
import com.hostelmanagement.enums.StudentStatus;
import com.hostelmanagement.repository.RoomRepository;
import com.hostelmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    public DashboardService(
            StudentRepository studentRepository,
            RoomRepository roomRepository
    ) {

        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    // DASHBOARD SUMMARY
    public DashboardResponse getDashboardSummary() {

        DashboardResponse response =
                new DashboardResponse();

        List<Student> students =
                studentRepository.findAll();

        List<Room> rooms =
                roomRepository.findAll();

        long activeStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus() ==
                                StudentStatus.ACTIVE)
                        .count();

        long vacatedStudents =
                students.stream()
                        .filter(s ->
                                s.getStatus() ==
                                StudentStatus.VACATED)
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

        response.setActiveStudents(activeStudents);

        response.setVacatedStudents(vacatedStudents);

        response.setTotalRooms(
                (long) rooms.size()
        );

        response.setOccupiedBeds(occupiedBeds);

        response.setAvailableBeds(
                totalBeds - occupiedBeds
        );

        response.setDueTodayCount(dueToday);

        response.setOverdueCount(overdue);

        return response;
    }

    // DUE TODAY
    public List<StudentResponse> getDueTodayStudents() {

        return studentRepository
                .findByNextDueDate(LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // OVERDUE
    public List<StudentResponse> getOverdueStudents() {

        return studentRepository
                .findByNextDueDateBefore(LocalDate.now())
                .stream()
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