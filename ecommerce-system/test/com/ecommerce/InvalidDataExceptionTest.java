package com.ecommerce;

import com.ecommerce.exceptions.InvalidDataException;
import com.ecommerce.exceptions.InvalidDataException.ErrorCode;

/**
 * Базові тести для InvalidDataException
 * Запускайте вручну через main для перевірки
 */
public class InvalidDataExceptionTest {

    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ InvalidDataException ===\n");

        testConstructorWithAllParameters();
        testConstructorWithCause();
        testGetDetailedMessage();
        testErrorCodes();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    private static void testConstructorWithAllParameters() {
        System.out.println("Тест 1: Конструктор з усіма параметрами");

        InvalidDataException exception = new InvalidDataException(
                "Тестова помилка",
                "testField",
                "invalidValue",
                ErrorCode.INVALID_FORMAT
        );

        assert exception.getMessage().contains("Тестова помилка") : "Повідомлення не містить текст";
        assert exception.getFieldName().equals("testField") : "Неправильне ім'я поля";
        assert exception.getInvalidValue().equals("invalidValue") : "Неправильне значення";
        assert exception.getErrorCode() == ErrorCode.INVALID_FORMAT : "Неправильний код помилки";

        System.out.println("✓ Тест пройдено\n");
    }

    private static void testConstructorWithCause() {
        System.out.println("Тест 2: Конструктор з причиною");

        Exception cause = new NumberFormatException("Invalid number");
        InvalidDataException exception = new InvalidDataException(
                "Помилка парсингу",
                "price",
                "abc",
                ErrorCode.INVALID_FORMAT,
                cause
        );

        assert exception.getCause() == cause : "Причина не збережена";
        assert exception.getMessage().contains("Помилка парсингу") : "Повідомлення втрачене";

        System.out.println("✓ Тест пройдено\n");
    }

    private static void testGetDetailedMessage() {
        System.out.println("Тест 3: Детальне повідомлення");

        InvalidDataException exception = new InvalidDataException(
                "Тест",
                "field",
                "value",
                ErrorCode.EMPTY_VALUE
        );

        String detailed = exception.getDetailedMessage();
        assert detailed.contains("InvalidDataException") : "Назва відсутня";
        assert detailed.contains("field") : "Поле відсутнє";
        assert detailed.contains("value") : "Значення відсутнє";
        assert detailed.contains("EMPTY_VALUE") : "Код помилки відсутній";

        System.out.println("Детальне повідомлення:");
        System.out.println(detailed);
        System.out.println("✓ Тест пройдено\n");
    }

    private static void testErrorCodes() {
        System.out.println("Тест 4: Коди помилок");

        ErrorCode[] codes = ErrorCode.values();
        assert codes.length > 0 : "Немає кодів помилок";

        for (ErrorCode code : codes) {
            assert code.getDescription() != null : "Відсутній опис для " + code;
            System.out.println("  " + code + ": " + code.getDescription());
        }

        System.out.println("✓ Тест пройдено\n");
    }
}
