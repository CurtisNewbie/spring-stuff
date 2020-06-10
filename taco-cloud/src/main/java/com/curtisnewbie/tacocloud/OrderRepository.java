package com.curtisnewbie.tacocloud;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    // this method's name is interpreted by Spring automatically, Order is known as a return type,
    // and zip is a field in Order
    List<Order> findByZip(String zip);
}
