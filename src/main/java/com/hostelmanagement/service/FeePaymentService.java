package com.hostelmanagement.service;

import com.hostelmanagement.dto.FeePaymentRequest;
import com.hostelmanagement.dto.FeePaymentResponse;
import com.hostelmanagement.entity.FeePayment;
import com.hostelmanagement.entity.Student;
import com.hostelmanagement.repository.FeePaymentRepository;
import com.hostelmanagement.repository.StudentRepository;
import com.hostelmanagement.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final StudentRepository studentRepository;
    private final JwtUtils jwtUtils;

    public FeePaymentService(
            FeePaymentRepository feePaymentRepository,
            StudentRepository studentRepository,
            JwtUtils jwtUtils
    ) {

        this.feePaymentRepository =
                feePaymentRepository;

        this.studentRepository =
                studentRepository;

        this.jwtUtils = jwtUtils;
    }

    // PAY FEE
    public FeePaymentResponse payFee(
            FeePaymentRequest request
    ) {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        Student student =
                studentRepository
                        .findByIdAndCustomerId(
                                request.getStudentId(),
                                customerId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found"
                                ));

        FeePayment payment =
                new FeePayment();

        payment.setStudent(student);

        payment.setCustomer(
                student.getCustomer()
        );

        payment.setAmountPaid(
                request.getAmountPaid()
        );

        payment.setPaymentDate(
                LocalDate.now()
        );

        payment.setPaymentMode(
                request.getPaymentMode()
        );

        payment.setRemarks(
                request.getRemarks()
        );

        LocalDate nextDueDate =
                student.getNextDueDate()
                            .plusMonths(1);

        payment.setNextDueDate(
                nextDueDate
        );

        FeePayment savedPayment =
                feePaymentRepository.save(payment);

        // UPDATE STUDENT DUE DATE
        student.setNextDueDate(nextDueDate);

        studentRepository.save(student);

        return mapToResponse(savedPayment);
    }

    // PAYMENT HISTORY
    public List<FeePaymentResponse>
    getPaymentHistory(Long studentId) {

        return feePaymentRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // MAP RESPONSE
    private FeePaymentResponse mapToResponse(
            FeePayment payment
    ) {

        FeePaymentResponse response =
                new FeePaymentResponse();

        response.setId(payment.getId());

        response.setStudentName(
                payment.getStudent()
                        .getFullName()
        );

        response.setAmountPaid(
                payment.getAmountPaid()
        );

        response.setPaymentDate(
                payment.getPaymentDate()
        );

        response.setNextDueDate(
                payment.getNextDueDate()
        );

        response.setPaymentMode(
                payment.getPaymentMode()
        );
        if (payment.getStudent() != null &&
        	    payment.getStudent().getRoom() != null) {

        	    response.setRoomNumber(
        	            payment.getStudent()
        	                    .getRoom()
        	                    .getRoomNumber()
        	    );
        	}
        return response;
    }
    
    public Double getCollectedThisMonth() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        LocalDate start =
                LocalDate.now()
                        .withDayOfMonth(1);

        LocalDate end =
                LocalDate.now();

        return feePaymentRepository
                .findByCustomerIdAndPaymentDateBetween(
                        customerId,
                        start,
                        end
                )
                .stream()
                .mapToDouble(
                        FeePayment::getAmountPaid
                )
                .sum();
    }
    
    public List<FeePaymentResponse>
    getPaymentsThisMonth() {

        Long customerId =
                jwtUtils.getRequiredCustomerId();

        LocalDate start =
                LocalDate.now()
                        .withDayOfMonth(1);

        LocalDate end =
                LocalDate.now();

        return feePaymentRepository
                .findByCustomerIdAndPaymentDateBetweenOrderByPaymentDateDesc(
                        customerId,
                        start,
                        end
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}