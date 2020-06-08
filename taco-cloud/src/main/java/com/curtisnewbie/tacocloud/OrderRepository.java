package com.curtisnewbie.tacocloud;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    // Order save(Order order);

    // this method's name is interpreted by Spring automatically, Order is known as a return type,
    // and zip is a field in Order
    List<Order> findByZip(String zip);

    // we can also use @Query to explicitly specify a SQL-alike query
    // @Query("Order o WHERE o.city='Seattle'")
    // List<Order> findOrdersInSeattle();
}
