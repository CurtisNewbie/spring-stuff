package com.curtisnewbie.payroll;

public class EmployeeNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2672479099126967135L;

    EmployeeNotFoundException(long id) {
        super(String.format("Employee '%d' is not found.", id));
    }
}