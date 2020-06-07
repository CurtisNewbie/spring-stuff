package com.curtisnewbie.tacocloud;


public class Order {

    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    public Order() {
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
        return "Order [ccCVV=" + ccCVV + ", ccExpration=" + ccExpiration + ", ccNumber=" + ccNumber
                + ", city=" + city + ", name=" + name + ", state=" + state + ", street=" + street
                + ", zip=" + zip + "]";
    }
}
