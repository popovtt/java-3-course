package com.ecommerce.enums;

public enum ProductCategory {
    ELECTRONICS("Електроніка", 0.02),
    CLOTHING("Одяг", 0.05),
    BOOKS("Книги", 0.0),
    FOOD("Їжа", 0.0),
    FURNITURE("Меблі", 0.03),
    TOYS("Іграшки", 0.04),
    SPORTS("Спорт", 0.03),
    BEAUTY("Краса", 0.04);

    private final String ukrainianName;
    private final double taxRate;

    ProductCategory(String ukrainianName, double taxRate) {
        this.ukrainianName = ukrainianName;
        this.taxRate = taxRate;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double calculateTax(double price) {
        return price * taxRate;
    }

    public double getPriceWithTax(double price) {
        return price + calculateTax(price);
    }

    public String getWarrantyPeriod() {
        return switch (this) {
            case ELECTRONICS -> "24 місяці";
            case FURNITURE -> "12 місяців";
            case CLOTHING, BOOKS, FOOD, TOYS, SPORTS, BEAUTY -> "14 днів (повернення)";
        };
    }

    public boolean isReturnable() {
        return this != FOOD;
    }

    public int getReturnDays() {
        return switch (this) {
            case ELECTRONICS -> 30;
            case CLOTHING, TOYS, SPORTS, BEAUTY -> 14;
            case FURNITURE -> 7;
            case BOOKS -> 30;
            case FOOD -> 0;
        };
    }

    @Override
    public String toString() {
        return String.format("%s (Податок: %.0f%%, Гарантія: %s)",
                ukrainianName, taxRate * 100, getWarrantyPeriod());
    }
}
