package com.curtisnewbie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Table(name = "TacoOrder")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip is required")
    private String zip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])(\\/)([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid Credit Card CCV")
    private String ccCVV;

    private Date placedAt;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Order() {
    }

    @PrePersist
    private void placedAt() {
        this.placedAt = new Date();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the ccNumber
     */
    public String getCcNumber() {
        return ccNumber;
    }

    /**
     * @param ccNumber the ccNumber to set
     */
    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    /**
     * @return the ccExpiration
     */
    public String getCcExpiration() {
        return ccExpiration;
    }

    /**
     * @param ccExpiration the ccExpiration to set
     */
    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    /**
     * @return the ccCVV
     */
    public String getCcCVV() {
        return ccCVV;
    }

    /**
     * @param ccCVV the ccCVV to set
     */
    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    @Override
    public String toString() {
        return "Order [ccCVV=" + ccCVV + ", ccExpration=" + ccExpiration + ", ccNumber=" + ccNumber + ", city=" + city
                + ", name=" + name + ", state=" + state + ", street=" + street + ", zip=" + zip + "]";
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
    public List<Taco> getTacos() {
        return tacos;
    }

    /**
     * @param tacos the tacos to set
     */
    public void setTacos(List<Taco> tacos) {
        this.tacos = tacos;
    }

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
