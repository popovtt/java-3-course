package com.ecommerce.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record CustomerInfo(
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate registrationDate,
        CustomerTier tier
) {

    public enum CustomerTier {
        BRONZE("Бронзовий", 0.0),
        SILVER("Срібний", 0.05),
        GOLD("Золотий", 0.10),
        PLATINUM("Платиновий", 0.15);

        private final String ukrainianName;
        private final double discountRate;

        CustomerTier(String ukrainianName, double discountRate) {
            this.ukrainianName = ukrainianName;
            this.discountRate = discountRate;
        }

        public String getUkrainianName() {
            return ukrainianName;
        }

        public double getDiscountRate() {
            return discountRate;
        }

        public CustomerTier upgrade() {
            return switch (this) {
                case BRONZE -> SILVER;
                case SILVER -> GOLD;
                case GOLD -> PLATINUM;
                case PLATINUM -> PLATINUM;
            };
        }
    }

    // Compact constructor
    public CustomerInfo {
        Objects.requireNonNull(firstName, "Ім'я не може бути null");
        Objects.requireNonNull(lastName, "Прізвище не може бути null");
        Objects.requireNonNull(email, "Email не може бути null");

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("Ім'я не може бути пустим");
        }
        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Прізвище не може бути пустим");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Невалідний email");
        }
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
        if (tier == null) {
            tier = CustomerTier.BRONZE;
        }
    }

    // Додаткові конструктори
    public CustomerInfo(String firstName, String lastName, String email, String phone) {
        this(firstName, lastName, email, phone, LocalDate.now(), CustomerTier.BRONZE);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getMembershipMonths() {
        return Period.between(registrationDate, LocalDate.now()).getMonths() +
                Period.between(registrationDate, LocalDate.now()).getYears() * 12;
    }

    public double getDiscount() {
        return tier.getDiscountRate();
    }

    public CustomerInfo upgradeTier() {
        return new CustomerInfo(firstName, lastName, email, phone,
                registrationDate, tier.upgrade());
    }

    public String getMembershipStatus() {
        int months = getMembershipMonths();
        if (months < 3) {
            return "🌱 Новий клієнт";
        } else if (months >= 3 && months < 12) {
            return "👤 Постійний клієнт";
        } else if (months >= 12 && months < 24) {
            return "⭐ VIP клієнт";
        } else if (months >= 24) {
            return "💎 Преміум клієнт";
        } else {
            return "Клієнт";
        }
    }

    // Factory methods
    public static CustomerInfo createCustomer(String firstName, String lastName, String email, String phone) {
        return new CustomerInfo(firstName, lastName, email, phone);
    }

    public static CustomerInfo createVIPCustomer(String firstName, String lastName,
                                                 String email, String phone) {
        return new CustomerInfo(firstName, lastName, email, phone,
                LocalDate.now(), CustomerTier.GOLD);
    }

    @Override
    public String toString() {
        return String.format("CustomerInfo[%s, email=%s, tier=%s (знижка %.0f%%), %s]",
                getFullName(), email, tier.getUkrainianName(),
                getDiscount() * 100, getMembershipStatus());
    }
}
