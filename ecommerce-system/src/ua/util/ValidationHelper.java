package ua.util;

import java.time.LocalDate;

class ValidationHelper {

    static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    static boolean isPositive(double value) {
        return value > 0;
    }

    static boolean isNonNegative(int value) {
        return value >= 0;
    }

    static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    static boolean isDateNotFuture(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    static boolean isValidPrice(double price) {
        return price >= 0 && price <= 1_000_000;
    }
}
