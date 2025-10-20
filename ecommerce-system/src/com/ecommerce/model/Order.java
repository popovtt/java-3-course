package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order extends Entity {

    private Customer customer;
    private List<Product> products;
    private LocalDate orderDate;
    private double totalAmount;

    public Order(Customer customer, List<Product> products, LocalDate orderDate) {
        super(orderDate);
        validateCustomer(customer);
        validateProducts(products);
        this.customer = customer;
        this.products = new ArrayList<>(products);
        this.orderDate = orderDate != null ? orderDate : LocalDate.now();
        calculateTotalAmount();
    }

    public Order(Customer customer, List<Product> products) {
        this(customer, products, LocalDate.now());
    }

    private Order() {
        super();
        this.products = new ArrayList<>();
    }

    // Factory methods
    public static Order createOrder(Customer customer, List<Product> products) {
        return new Order(customer, products);
    }

    public static Order createOrderWithDate(Customer customer, List<Product> products, LocalDate date) {
        return new Order(customer, products, date);
    }

    public static Order createEmptyOrder(Customer customer) {
        Order order = new Order();
        order.customer = customer;
        order.orderDate = LocalDate.now();
        order.totalAmount = 0.0;
        return order;
    }

    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Клієнт не може бути null");
        }
    }

    private void validateProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Список товарів не може бути пустим");
        }
    }

    private void calculateTotalAmount() {
        this.totalAmount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    // Getters
    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не може бути null");
        }
        products.add(product);
        calculateTotalAmount();
    }

    public int getProductCount() {
        return products.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return Double.compare(order.totalAmount, totalAmount) == 0 &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(products, order.products) &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, products, orderDate, totalAmount);
    }

    @Override
    public String toString() {
        return String.format("Order{customer=%s, productsCount=%d, orderDate=%s, totalAmount=%s}",
                customer.getFullName(), getProductCount(), Utils.formatDate(orderDate),
                Utils.formatPrice(totalAmount));
    }
}
