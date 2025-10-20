package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.Objects;

public class Product extends Entity {

    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock, LocalDate createdDate) {
        super(createdDate);
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public Product(String name, double price, int stock) {
        this(name, price, stock, LocalDate.now());
    }

    private Product() {
        super();
    }

    // Factory methods
    public static Product createProduct(String name, double price, int stock) {
        return new Product(name, price, stock);
    }

    public static Product createProductWithDate(String name, double price, int stock, LocalDate date) {
        return new Product(name, price, stock, date);
    }

    public static Product createDefaultProduct() {
        Product product = new Product();
        product.name = "Товар за замовчуванням";
        product.price = 0.0;
        product.stock = 0;
        return product;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Utils.validateString(name, "Назва товару");
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        Utils.validatePrice(price);
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        Utils.validateNonNegative(stock, "Кількість на складі");
        this.stock = stock;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public void decreaseStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Недостатньо товару на складі");
        }
        this.stock -= quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Double.compare(product.price, price) == 0 &&
                stock == product.stock &&
                Objects.equals(name, product.name) &&
                Objects.equals(createdDate, product.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, stock, createdDate);
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', price=%s, stock=%d, createdDate=%s, inStock=%s}",
                name, Utils.formatPrice(price), stock, Utils.formatDate(createdDate), isInStock());
    }
}
