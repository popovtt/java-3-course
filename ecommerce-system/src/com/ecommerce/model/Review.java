package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.Objects;

public class Review extends Entity {

    private Product product;
    private Customer customer;
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    public Review(Product product, Customer customer, int rating, String comment, LocalDate reviewDate) {
        super(reviewDate);
        validateProduct(product);
        validateCustomer(customer);
        this.product = product;
        this.customer = customer;
        setRating(rating);
        setComment(comment);
        this.reviewDate = reviewDate != null ? reviewDate : LocalDate.now();
    }

    public Review(Product product, Customer customer, int rating, String comment) {
        this(product, customer, rating, comment, LocalDate.now());
    }

    private Review() {
        super();
    }

    // Factory methods
    public static Review createReview(Product product, Customer customer, int rating, String comment) {
        return new Review(product, customer, rating, comment);
    }

    public static Review createReviewWithDate(Product product, Customer customer, int rating,
                                              String comment, LocalDate date) {
        return new Review(product, customer, rating, comment, date);
    }

    public static Review createQuickReview(Product product, Customer customer, int rating) {
        Review review = new Review();
        review.product = product;
        review.customer = customer;
        review.rating = rating;
        review.comment = "Без коментарів";
        review.reviewDate = LocalDate.now();
        return review;
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не може бути null");
        }
    }

    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Клієнт не може бути null");
        }
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        Utils.validateRating(rating);
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        Utils.validateString(comment, "Коментар");
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    protected void setReviewDate(LocalDate reviewDate) {
        Utils.validateDate(reviewDate, "Дата відгуку");
        this.reviewDate = reviewDate;
    }

    public String getRatingStars() {
        return "★".repeat(rating) + "☆".repeat(5 - rating);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Review review = (Review) obj;
        return rating == review.rating &&
                Objects.equals(product, review.product) &&
                Objects.equals(customer, review.customer) &&
                Objects.equals(comment, review.comment) &&
                Objects.equals(reviewDate, review.reviewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, customer, rating, comment, reviewDate);
    }

    @Override
    public String toString() {
        return String.format("Review{product='%s', customer='%s', rating=%s, comment='%s', reviewDate=%s}",
                product.getName(), customer.getFullName(), getRatingStars(), comment,
                Utils.formatDate(reviewDate));
    }
}
