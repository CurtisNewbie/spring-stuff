package com.curtisnewbie.controllers;

import java.util.Optional;
import com.curtisnewbie.model.Order;
import com.curtisnewbie.model.OrderModel;
import com.curtisnewbie.model.OrderModelAssembler;
import com.curtisnewbie.model.OrderRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private OrderRepository orderRepo;
    private OrderModelAssembler orderModelAssembler;

    public OrderController(OrderRepository orderRepo, OrderModelAssembler orderModelAssembler) {
        this.orderRepo = orderRepo;
        this.orderModelAssembler = orderModelAssembler;
    }

    // PUT can be considered as replacement of data
    @PutMapping("/{orderId}")
    public OrderModel putOrder(@RequestBody Order order) {
        return orderModelAssembler.toModel(orderRepo.save(order));
    }

    @GetMapping("/{orderId}")
    public OrderModel getOrder(@PathVariable("orderId") Long orderId) {
        Optional<Order> opt = orderRepo.findById(orderId);
        if (opt.isPresent())
            return orderModelAssembler.toModel(opt.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found");
    }

    // POST can be considered as replacement of partial data (for this demo, we
    // consider non-null data, but data can be null)
    @PatchMapping(path = "/{orderId}")
    public OrderModel patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Order order = orderRepo.findById(orderId).get();
        // replace all non-null values
        if (patch.getName() != null)
            order.setName(patch.getName());
        if (patch.getStreet() != null)
            order.setStreet(patch.getName());
        if (patch.getCity() != null)
            order.setCity(patch.getCity());
        if (patch.getState() != null)
            order.setState(patch.getState());
        if (patch.getZip() != null)
            order.setZip(patch.getZip());
        if (patch.getCcNumber() != null)
            order.setCcNumber(patch.getCcNumber());
        if (patch.getCcExpiration() != null)
            order.setCcExpiration(patch.getCcExpiration());
        if (patch.getCcCVV() != null)
            order.setCcCVV(patch.getCcCVV());
        return orderModelAssembler.toModel(orderRepo.save(order));
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) { // we may return 404 here if no entity is found
        }
    }
}
