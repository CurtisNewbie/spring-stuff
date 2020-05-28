package com.curtisnewbie.payroll;

public class OrderNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5166532217003183741L;

    OrderNotFoundException(Long id) {
        super(String.format("Order '%d' not found.", id));
    }
}