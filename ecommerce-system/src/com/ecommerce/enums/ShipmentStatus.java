package com.ecommerce.enums;

public enum ShipmentStatus {
    PENDING("Очікується", "Відправка ще не розпочата"),
    PREPARING("Підготовка", "Підготовка до відправки"),
    IN_TRANSIT("В дорозі", "Посилка в дорозі"),
    OUT_FOR_DELIVERY("На доставці", "Посилка передана кур'єру"),
    DELIVERED("Доставлено", "Посилка доставлена"),
    FAILED("Невдала доставка", "Доставка не вдалася"),
    RETURNED("Повернуто", "Посилка повернута відправнику");

    private final String ukrainianName;
    private final String description;

    ShipmentStatus(String ukrainianName, String description) {
        this.ukrainianName = ukrainianName;
        this.description = description;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInProgress() {
        return this == PREPARING || this == IN_TRANSIT || this == OUT_FOR_DELIVERY;
    }

    public boolean isCompleted() {
        return this == DELIVERED;
    }

    public boolean isFailed() {
        return this == FAILED || this == RETURNED;
    }

    public ShipmentStatus nextStatus() {
        return switch (this) {
            case PENDING -> PREPARING;
            case PREPARING -> IN_TRANSIT;
            case IN_TRANSIT -> OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> DELIVERED;
            case DELIVERED, FAILED, RETURNED ->
                    throw new IllegalStateException("Відправка завершена");
        };
    }

    public String getIcon() {
        return switch (this) {
            case PENDING -> "⏳";
            case PREPARING -> "📦";
            case IN_TRANSIT -> "🚚";
            case OUT_FOR_DELIVERY -> "🚴";
            case DELIVERED -> "✅";
            case FAILED -> "❌";
            case RETURNED -> "↩️";
        };
    }

    public int getProgressPercentage() {
        return switch (this) {
            case PENDING -> 0;
            case PREPARING -> 20;
            case IN_TRANSIT -> 50;
            case OUT_FOR_DELIVERY -> 80;
            case DELIVERED -> 100;
            case FAILED, RETURNED -> 0;
        };
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s (%d%%)",
                getIcon(), ukrainianName, description, getProgressPercentage());
    }
}
