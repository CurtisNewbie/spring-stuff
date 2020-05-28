package com.curtisnewbie.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    // CommandLineRunner beans are all ran by spring boot on startup
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository empRepo, OrderRepository ordRepo) {
        return args -> {
            empRepo.save(new Employee("Curtis", "Newbie"));
            empRepo.save(new Employee("Bilbo Baggins", "Burglar"));
            ordRepo.save(new Order("MacBook Pro", Status.COMPLETED));
            ordRepo.save(new Order("iPhone", Status.IN_PROGRESS));
            empRepo.findAll().forEach(e -> logger.info(e.toString()));
            ordRepo.findAll().forEach(o -> logger.info(o.toString()));
        };
    }
}