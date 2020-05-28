package com.curtisnewbie.payroll;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * For {@code Employee} where the primary key is {@code Long}
 * </p>
 */
interface EmployeeRepository extends JpaRepository<Employee, Long> {

}