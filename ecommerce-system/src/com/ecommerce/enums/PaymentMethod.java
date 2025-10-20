package com.ecommerce.enums;

public enum PaymentMethod {
    CASH("Готівка", 0.0, true),
    CREDIT_CARD("Кредитна картка", 0.02, true),
    DEBIT_CARD("Дебетова картка", 0.015, true),
    PAYPAL("PayPal", 0.03, true),
    BANK_TRANSFER("Банківський переказ", 0.01, false),
    CRYPTO("Криптовалюта", 0.01, true);

    private final String ukrainianName;
    private final double commissionRate;
    private final boolean instantProcessing;

    PaymentMethod(String ukrainianName, double commissionRate, boolean instantProcessing) {
        this.ukrainianName = ukrainianName;
        this.commissionRate = commissionRate;
        this.instantProcessing = instantProcessing;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public boolean isInstantProcessing() {
        return instantProcessing;
    }

    public double calculateCommission(double amount) {
        return amount * commissionRate;
    }

    public double getTotalAmount(double amount) {
        return amount + calculateCommission(amount);
    }

    public String getProcessingTime() {
        return switch (this) {
            case CASH -> "Миттєво";
            case CREDIT_CARD, DEBIT_CARD, PAYPAL -> "До 5 хвилин";
            case BANK_TRANSFER -> "1-3 робочих дні";
            case CRYPTO -> "15-30 хвилин";
        };
    }

    public boolean requiresVerification() {
        return switch (this) {
            case CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER -> true;
            case CASH, PAYPAL, CRYPTO -> false;
        };
    }

    @Override
    public String toString() {
        return String.format("%s (Комісія: %.1f%%, Час: %s)",
                ukrainianName, commissionRate * 100, getProcessingTime());
    }
}
