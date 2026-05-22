package com.hostelmanagement.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hostelmanagement.dto.LoginRequest;
import com.hostelmanagement.dto.RegisterRequest;
import com.hostelmanagement.entity.Customer;
import com.hostelmanagement.repository.CustomerRepository;
import com.hostelmanagement.security.JwtUtil;

@Service
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public String register(RegisterRequest request) {

        String email =
                request.getEmail().trim();

        if (customerRepository.existsByEmail(email)) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        Customer customer = new Customer();

        customer.setHostelName(
                request.getHostelName()
        );

        customer.setOwnerName(
                request.getOwnerName()
        );

        customer.setEmail(email);

        customer.setPassword(
                passwordEncoder.encode(
                        request.getPassword().trim()
                )
        );

        customer.setPhone(
                request.getPhone()
        );

        customer.setAddress(
                request.getAddress()
        );

        customer.setStatus("ACTIVE");

        customerRepository.save(customer);

        return "Hostel registered successfully";
    }

    // LOGIN
    public Map<String, Object> login(
            LoginRequest request
    ) {

        String email =
                request.getEmail().trim();

        String password =
                request.getPassword().trim();

        Customer customer =
                customerRepository
                        .findByEmailAndStatus(
                                email,
                                "ACTIVE"
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                ));

        boolean passwordMatches =
                passwordEncoder.matches(
                        password,
                        customer.getPassword()
                );

        if (!passwordMatches) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtUtil.generateToken(
                        customer.getId()
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put("token", token);

        response.put(
                "customerId",
                customer.getId()
        );

        response.put(
                "hostelName",
                customer.getHostelName()
        );

        response.put(
                "ownerName",
                customer.getOwnerName()
        );

        response.put(
                "email",
                customer.getEmail()
        );

        return response;
    }
}