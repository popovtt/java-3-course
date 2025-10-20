package com.ecommerce.enums;

public enum OrderStatus {
    PENDING("Очікується", "Замовлення створене, очікує обробки"),
    CONFIRMED("Підтверджено", "Замовлення підтверджене"),
    PROCESSING("Обробляється", "Замовлення в обробці"),
    SHIPPED("Відправлено", "Замовлення відправлене"),
    DELIVERED("Доставлено", "Замовлення доставлене"),
    CANCELLED("Скасовано", "Замовлення скасоване");

    private final String ukrainianName;
    private final String description;

    OrderStatus(String ukrainianName, String description) {
        this.ukrainianName = ukrainianName;
        this.description = description;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return this != CANCELLED && this != DELIVERED;
    }

    public boolean canCancel() {
        return this == PENDING || this == CONFIRMED;
    }

    public boolean canShip() {
        return this == PROCESSING;
    }

    public OrderStatus nextStatus() {
        return switch (this) {
            case PENDING -> CONFIRMED;
            case CONFIRMED -> PROCESSING;
            case PROCESSING -> SHIPPED;
            case SHIPPED -> DELIVERED;
            case DELIVERED -> throw new IllegalStateException("Замовлення вже доставлене");
            case CANCELLED -> throw new IllegalStateException("Не можна змінити статус скасованого замовлення");
        };
    }

    public String getIcon() {
        return switch (this) {
            case PENDING -> "⏳";
            case CONFIRMED -> "✅";
            case PROCESSING -> "📦";
            case SHIPPED -> "🚚";
            case DELIVERED -> "✓";
            case CANCELLED -> "❌";
        };
    }

    public int getEstimatedDays() {
        return switch (this) {
            case PENDING -> 0;
            case CONFIRMED -> 1;
            case PROCESSING -> 2;
            case SHIPPED -> 5;
            case DELIVERED, CANCELLED -> 0;
        };
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", getIcon(), ukrainianName, description);
    }
}
