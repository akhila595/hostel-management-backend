package com.hostel_management.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtUtils {

    public Long getCustomerId() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes)
                        RequestContextHolder
                                .getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        HttpServletRequest request =
                attributes.getRequest();

        Object customerId =
                request.getAttribute("customerId");

        if (customerId == null) {
            return null;
        }

        return (Long) customerId;
    }

    public Long getRequiredCustomerId() {

        Long customerId = getCustomerId();

        if (customerId == null) {
            throw new RuntimeException(
                    "Unauthorized"
            );
        }

        return customerId;
    }
}