package com.hostelmanagement.service;

import com.hostelmanagement.dto.StudentRequest;
import com.hostelmanagement.dto.StudentResponse;
import com.hostelmanagement.entity.Bed;
import com.hostelmanagement.entity.Room;
import com.hostelmanagement.entity.Student;
import com.hostelmanagement.enums.BedStatus;
import com.hostelmanagement.enums.StudentStatus;
import com.hostelmanagement.repository.BedRepository;
import com.hostelmanagement.repository.RoomRepository;
import com.hostelmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public StudentService(StudentRepository studentRepository,
                          RoomRepository roomRepository,
                          BedRepository bedRepository) {

        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    // CREATE STUDENT
    public StudentResponse createStudent(StudentRequest request) {

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new RuntimeException("Room not found"));

        Bed bed = bedRepository.findById(request.getBedId())
                .orElseThrow(() ->
                        new RuntimeException("Bed not found"));

        if (bed.getStatus() == BedStatus.OCCUPIED) {
            throw new RuntimeException("Bed already occupied");
        }

        Student student = new Student();

        student.setFullName(request.getFullName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setParentPhone(request.getParentPhone());
        student.setAadhaarNumber(request.getAadhaarNumber());
        student.setJoiningDate(request.getJoiningDate());
        student.setFeeAmount(request.getFeeAmount());

        // NEXT DUE DATE = JOINING DATE + 30 DAYS
        student.setNextDueDate(
                request.getJoiningDate().plusDays(30)
        );

        student.setStatus(StudentStatus.ACTIVE);
        student.setAddress(request.getAddress());
        student.setRemarks(request.getRemarks());

        student.setRoom(room);
        student.setBed(bed);

        Student savedStudent = studentRepository.save(student);

        // UPDATE BED STATUS
        bed.setStatus(BedStatus.OCCUPIED);
        bedRepository.save(bed);

        // UPDATE ROOM OCCUPIED COUNT
        room.setOccupiedBeds(room.getOccupiedBeds() + 1);
        roomRepository.save(room);

        return mapToResponse(savedStudent);
    }

    // GET ALL STUDENTS
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET STUDENT BY ID
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        return mapToResponse(student);
    }

    // UPDATE STUDENT
    public StudentResponse updateStudent(Long id,
                                         StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        student.setFullName(request.getFullName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setParentPhone(request.getParentPhone());
        student.setFeeAmount(request.getFeeAmount());
        student.setAddress(request.getAddress());
        student.setRemarks(request.getRemarks());

        Student updatedStudent =
                studentRepository.save(student);

        return mapToResponse(updatedStudent);
    }

    // VACATE STUDENT
    public void vacateStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        student.setStatus(StudentStatus.VACATED);

        studentRepository.save(student);

        // FREE BED
        Bed bed = student.getBed();

        bed.setStatus(BedStatus.AVAILABLE);

        bedRepository.save(bed);

        // UPDATE ROOM COUNT
        Room room = student.getRoom();

        room.setOccupiedBeds(room.getOccupiedBeds() - 1);

        roomRepository.save(room);
    }

    // MAP RESPONSE
    private StudentResponse mapToResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setFullName(student.getFullName());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setFeeAmount(student.getFeeAmount());
        response.setJoiningDate(student.getJoiningDate());
        response.setNextDueDate(student.getNextDueDate());

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