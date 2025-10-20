package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.Objects;

public class Customer extends Entity {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registrationDate;

    public Customer(String firstName, String lastName, String email, LocalDate registrationDate) {
        super(registrationDate);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        this.registrationDate = registrationDate != null ? registrationDate : LocalDate.now();
    }

    public Customer(String firstName, String lastName, String email) {
        this(firstName, lastName, email, LocalDate.now());
    }

    private Customer() {
        super();
    }

    // Factory methods
    public static Customer createCustomer(String firstName, String lastName, String email) {
        return new Customer(firstName, lastName, email);
    }

    public static Customer createCustomerWithDate(String firstName, String lastName, String email, LocalDate date) {
        return new Customer(firstName, lastName, email, date);
    }

    public static Customer createGuestCustomer() {
        Customer customer = new Customer();
        customer.firstName = "Гість";
        customer.lastName = "Гостевий";
        customer.email = "guest@example.com";
        customer.registrationDate = LocalDate.now();
        return customer;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Utils.validateString(firstName, "Ім'я");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        Utils.validateString(lastName, "Прізвище");
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Utils.validateEmail(email);
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    protected void setRegistrationDate(LocalDate registrationDate) {
        Utils.validateDate(registrationDate, "Дата реєстрації");
        this.registrationDate = registrationDate;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(registrationDate, customer.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, registrationDate);
    }

    @Override
    public String toString() {
        return String.format("Customer{fullName='%s', email='%s', registrationDate=%s}",
                getFullName(), email, Utils.formatDate(registrationDate));
    }
}
