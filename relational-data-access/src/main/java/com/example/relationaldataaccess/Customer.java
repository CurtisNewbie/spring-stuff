package com.example.relationaldataaccess;

public class Customer {

    private Long id;
    private String firstName, lastName;

    public Customer(Long id, String fname, String lname) {
        this.id = id;
        this.firstName = fname;
        this.lastName = lname;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer {id: %d, firstName: '%s', lastName: '%s'}", getId(), getFirstName(),
                getLastName());
    }
}