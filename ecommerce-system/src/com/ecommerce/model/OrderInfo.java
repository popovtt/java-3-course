package com.ecommerce.model;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record OrderInfo(
        String orderId,
        CustomerInfo customer,
        List<ProductInfo> products,
        LocalDate orderDate,
        OrderStatus status,
        PaymentMethod paymentMethod
) {

    // Compact constructor
    public OrderInfo {
        Objects.requireNonNull(orderId, "ID замовлення не може бути null");
        Objects.requireNonNull(customer, "Клієнт не може бути null");
        Objects.requireNonNull(products, "Список товарів не може бути null");
        Objects.requireNonNull(status, "Статус не може бути null");
        Objects.requireNonNull(paymentMethod, "Метод оплати не може бути null");

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Список товарів не може бути пустим");
        }
        if (orderDate == null) {
            orderDate = LocalDate.now();
        }

        products = List.copyOf(products);
    }

    public double getSubtotal() {
        return products.stream()
                .mapToDouble(ProductInfo::price)
                .sum();
    }

    public double getTaxTotal() {
        return products.stream()
                .mapToDouble(ProductInfo::getTaxAmount)
                .sum();
    }

    public double getDiscount() {
        return getSubtotal() * customer.getDiscount();
    }

    public double getPaymentCommission() {
        return paymentMethod.calculateCommission(getSubtotal() + getTaxTotal() - getDiscount());
    }

    public double getTotalAmount() {
        double subtotal = getSubtotal();
        double tax = getTaxTotal();
        double discount = getDiscount();
        double commission = paymentMethod.calculateCommission(subtotal + tax - discount);
        return subtotal + tax - discount + commission;
    }

    public int getProductCount() {
        return products.size();
    }

    public OrderInfo withStatus(OrderStatus newStatus) {
        return new OrderInfo(orderId, customer, products, orderDate, newStatus, paymentMethod);
    }

    public OrderInfo addProduct(ProductInfo product) {
        List<ProductInfo> updatedProducts = new java.util.ArrayList<>(products);
        updatedProducts.add(product);
        return new OrderInfo(orderId, customer, updatedProducts, orderDate, status, paymentMethod);
    }

    public String getOrderSummary() {
        return switch (status) {
            case PENDING -> String.format("⏳ Очікує обробки | Сума: %.2f грн", getTotalAmount());
            case CONFIRMED -> String.format("✅ Підтверджено | Очікуваний час: %d днів",
                    status.getEstimatedDays());
            case PROCESSING -> String.format("📦 Обробляється | Товарів: %d", getProductCount());
            case SHIPPED -> String.format("🚚 Відправлено | Очікується доставка: %d днів",
                    status.getEstimatedDays());
            case DELIVERED -> String.format("✓ Доставлено | Всього: %.2f грн", getTotalAmount());
            case CANCELLED -> "❌ Скасовано";
        };
    }

    public boolean canBeCancelled() {
        return status.canCancel();
    }

    // Factory method
    public static OrderInfo createOrder(CustomerInfo customer, List<ProductInfo> products,
                                        PaymentMethod paymentMethod) {
        String id = "ORD-" + System.currentTimeMillis();
        return new OrderInfo(id, customer, products, LocalDate.now(),
                OrderStatus.PENDING, paymentMethod);
    }

    @Override
    public String toString() {
        return String.format("OrderInfo[%s, customer=%s, products=%d, date=%s, " +
                        "total=%.2f грн, status=%s]",
                orderId, customer.getFullName(), getProductCount(), orderDate,
                getTotalAmount(), status.getUkrainianName());
    }
}
