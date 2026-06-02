package com.hostelmanagement.repository;

import com.hostelmanagement.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeePaymentRepository
        extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByStudentId(
            Long studentId
    );

    List<FeePayment> findByCustomerId(
            Long customerId
    );
    
    List<FeePayment> findByCustomerIdAndPaymentDateBetween(
            Long customerId,
            LocalDate startDate,
            LocalDate endDate
    );
    
    List<FeePayment> findByCustomerIdAndPaymentDateBetweenOrderByPaymentDateDesc(
            Long customerId,
            LocalDate startDate,
            LocalDate endDate
    );
}