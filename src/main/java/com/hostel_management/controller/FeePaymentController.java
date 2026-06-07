package com.hostel_management.controller;

import com.hostel_management.dto.FeePaymentRequest;
import com.hostel_management.dto.FeePaymentResponse;
import com.hostel_management.service.FeePaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin("*")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    public FeePaymentController(
            FeePaymentService feePaymentService
    ) {
        this.feePaymentService = feePaymentService;
    }

    @PostMapping
    public FeePaymentResponse payFee(
            @RequestBody FeePaymentRequest request
    ) {
        return feePaymentService.payFee(request);
    }

    @GetMapping("/student/{studentId}")
    public List<FeePaymentResponse> getPaymentHistory(
            @PathVariable Long studentId
    ) {
        return feePaymentService
                .getPaymentHistory(studentId);
    }
    
    @GetMapping("/this-month")
    public List<FeePaymentResponse> getPaymentsThisMonth() {

        return feePaymentService
                .getPaymentsThisMonth();
    }
    
}