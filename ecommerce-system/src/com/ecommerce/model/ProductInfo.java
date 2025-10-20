package com.ecommerce.model;

import com.ecommerce.enums.ProductCategory;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.Objects;

public record ProductInfo(
        String name,
        double price,
        int stock,
        ProductCategory category,
        LocalDate createdDate
) {

    // Compact constructor для валідації
    public ProductInfo {
        Objects.requireNonNull(name, "Назва товару не може бути null");
        Objects.requireNonNull(category, "Категорія не може бути null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("Назва товару не може бути пустою");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Кількість не може бути від'ємною");
        }
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
    }

    // Додаткові конструктори
    public ProductInfo(String name, double price, int stock, ProductCategory category) {
        this(name, price, stock, category, LocalDate.now());
    }

    // Методи для роботи з товаром
    public boolean isInStock() {
        return stock > 0;
    }

    public double getPriceWithTax() {
        return category.getPriceWithTax(price);
    }

    public double getTaxAmount() {
        return category.calculateTax(price);
    }

    public ProductInfo decreaseStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Недостатньо товару на складі");
        }
        return new ProductInfo(name, price, stock - quantity, category, createdDate);
    }

    public ProductInfo increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Кількість має бути додатньою");
        }
        return new ProductInfo(name, price, stock + quantity, category, createdDate);
    }

    public ProductInfo updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
        return new ProductInfo(name, newPrice, stock, category, createdDate);
    }

    public String getStockStatus() {
        if (stock == 0) {
            return "❌ Немає в наявності";
        } else if (stock > 0 && stock <= 5) {
            return "⚠️ Закінчується (залишилось " + stock + ")";
        } else if (stock > 5 && stock <= 20) {
            return "✓ В наявності (" + stock + " шт.)";
        } else if (stock > 20) {
            return "✓✓ Багато в наявності (" + stock + " шт.)";
        } else {
            return "Невідомо";
        }
    }

    // Factory methods
    public static ProductInfo createProduct(String name, double price, int stock, ProductCategory category) {
        return new ProductInfo(name, price, stock, category);
    }

    public static ProductInfo createOutOfStock(String name, double price, ProductCategory category) {
        return new ProductInfo(name, price, 0, category);
    }

    @Override
    public String toString() {
        return String.format("ProductInfo[name='%s', price=%.2f грн (з податком: %.2f грн), " +
                        "stock=%d, category=%s, %s]",
                name, price, getPriceWithTax(), stock, category.getUkrainianName(), getStockStatus());
    }
}
