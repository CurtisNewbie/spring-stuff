package com.curtisnewbie.model;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;

public class OrderModel extends RepresentationModel<OrderModel> {

    @Autowired
    private TacoModelAssembler tacoModelAssembler;
    private final String name;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String ccNumber;
    private final String ccExpiration;
    private final String ccCVV;
    private final Date placedAt;
    private final List<TacoModel> tacos;

    public OrderModel(Order order) {
        this.name = order.getName();
        this.street = order.getStreet();
        this.city = order.getCity();
        this.state = order.getState();
        this.zip = order.getZip();
        this.ccNumber = order.getCcNumber();
        this.ccExpiration = order.getCcExpiration();
        this.ccCVV = order.getCcCVV();
        this.placedAt = order.getPlacedAt();
        this.tacos = tacoModelAssembler.toModelsList(order.getTacos());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @return the ccNumber
     */
    public String getCcNumber() {
        return ccNumber;
    }

    /**
     * @return the ccExpiration
     */
    public String getCcExpiration() {
        return ccExpiration;
    }

    /**
     * @return the ccCVV
     */
    public String getCcCVV() {
        return ccCVV;
    }

    /**
     * @return the placedAt
     */
    public Date getPlacedAt() {
        return placedAt;
    }

    /**
     * @return the tacos
     */
    public List<TacoModel> getTacos() {
        return tacos;
    }
}
