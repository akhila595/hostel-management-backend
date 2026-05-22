package com.hostelmanagement.repository;

import com.hostelmanagement.entity.Student;
import com.hostelmanagement.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    List<Student> findByStatus(
            StudentStatus status
    );

    List<Student> findByNextDueDate(
            LocalDate dueDate
    );

    List<Student> findByNextDueDateBefore(
            LocalDate date
    );

    List<Student> findByCustomerId(
            Long customerId
    );

    Optional<Student> findByIdAndCustomerId(
            Long id,
            Long customerId
    );
}