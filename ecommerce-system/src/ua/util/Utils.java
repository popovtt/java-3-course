package ua.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void validateEmail(String email) {
        if (!ValidationHelper.isValidEmail(email)) {
            throw new IllegalArgumentException("Невалідний email: " + email);
        }
    }

    public static void validateString(String value, String fieldName) {
        if (!ValidationHelper.isValidString(value)) {
            throw new IllegalArgumentException(fieldName + " не може бути пустим");
        }
    }

    public static void validatePositive(double value, String fieldName) {
        if (!ValidationHelper.isPositive(value)) {
            throw new IllegalArgumentException(fieldName + " має бути більше 0");
        }
    }

    public static void validateNonNegative(int value, String fieldName) {
        if (!ValidationHelper.isNonNegative(value)) {
            throw new IllegalArgumentException(fieldName + " не може бути від'ємним");
        }
    }

    public static void validateRating(int rating) {
        if (!ValidationHelper.isValidRating(rating)) {
            throw new IllegalArgumentException("Рейтинг має бути від 1 до 5");
        }
    }

    public static void validateDate(LocalDate date, String fieldName) {
        if (!ValidationHelper.isDateNotFuture(date)) {
            throw new IllegalArgumentException(fieldName + " не може бути в майбутньому");
        }
    }

    public static void validatePrice(double price) {
        if (!ValidationHelper.isValidPrice(price)) {
            throw new IllegalArgumentException("Ціна має бути в діапазоні від 0 до 1,000,000");
        }
    }

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "Дата не вказана";
    }

    public static String formatPrice(double price) {
        return String.format("%.2f грн", price);
    }
}
