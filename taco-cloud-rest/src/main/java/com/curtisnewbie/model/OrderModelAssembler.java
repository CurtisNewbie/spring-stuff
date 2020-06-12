package com.curtisnewbie.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.curtisnewbie.controllers.OrderController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(Order order) {
        OrderModel orderModel = new OrderModel(order);
        orderModel.add(linkToSelf(order));
        return orderModel;
    }

    public CollectionModel<OrderModel> toModels(Collection<Order> orders) {
        return CollectionModel.of(toModelsList(orders));
    }

    public List<OrderModel> toModelsList(Collection<Order> orders) {
        List<OrderModel> list = new ArrayList<>();
        orders.forEach(o -> list.add(toModel(o)));
        return list;
    }

    public static Link linkToSelf(Order order) {
        return WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrder(order.getId()))
                .withSelfRel();
    }


}
