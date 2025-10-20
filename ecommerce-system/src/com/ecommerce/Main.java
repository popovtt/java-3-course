package com.ecommerce;

import com.ecommerce.exceptions.InvalidDataException;
import com.ecommerce.model.CustomerInfo;
import com.ecommerce.model.ProductInfo;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("ЗАПУСК E-COMMERCE SYSTEM - ЛР 3: Обробка виключень");
        logger.info("=".repeat(80));

        try {
            // Демонстрація 1: Завантаження товарів
            demonstrateProductLoading();

            // Демонстрація 2: Завантаження клієнтів
            demonstrateCustomerLoading();

            // Демонстрація 3: Обробка помилок
            demonstrateErrorHandling();

            // Демонстрація 4: Multi-catch
            demonstrateMultiCatch();

            // Демонстрація 5: Try-with-resources
            demonstrateTryWithResources();

            logger.info("=".repeat(80));
            logger.info("✓ Програма завершена успішно!");
            logger.info("=".repeat(80));

        } catch (Exception e) {
            logger.error("Критична помилка в main: " + e.getMessage(), e);
        }
    }

    private static void demonstrateProductLoading() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. ЗАВАНТАЖЕННЯ ТОВАРІВ З ФАЙЛУ");
        System.out.println("=".repeat(80));

        ProductService productService = new ProductService();

        try {
            logger.info("Спроба завантаження товарів...");
            List<ProductInfo> products = productService.loadProducts();

            System.out.println("\n✓ Успішно завантажено " + products.size() + " товарів:");
            products.forEach(p -> System.out.println("  • " + p));

        } catch (FileNotFoundException e) {
            logger.error("Файл не знайдено", e);
            System.err.println("❌ ПОМИЛКА: Файл не знайдено - " + e.getMessage());

        } catch (InvalidDataException e) {
            logger.error("Невалідні дані", e);
            System.err.println("❌ ПОМИЛКА: " + e.getDetailedMessage());

        } catch (IOException e) {
            logger.error("Помилка вводу/виводу", e);
            System.err.println("❌ ПОМИЛКА: Проблема з читанням файлу - " + e.getMessage());

        } finally {
            logger.info("Завершення завантаження товарів");
            System.out.println("\n[FINALLY] Блок finally виконано (закриття ресурсів)");
        }
    }

    private static void demonstrateCustomerLoading() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. ЗАВАНТАЖЕННЯ КЛІЄНТІВ З ФАЙЛУ");
        System.out.println("=".repeat(80));

        CustomerService customerService = new CustomerService();

        try {
            logger.info("Спроба завантаження клієнтів...");
            List<CustomerInfo> customers = customerService.loadCustomers();

            System.out.println("\n✓ Успішно завантажено " + customers.size() + " клієнтів:");
            customers.forEach(c -> System.out.println("  • " + c));

        } catch (FileNotFoundException e) {
            logger.error("Файл клієнтів не знайдено", e);
            System.err.println("❌ ПОМИЛКА: " + e.getMessage());

        } catch (InvalidDataException e) {
            logger.error("Невалідні дані клієнта", e);
            System.err.println("❌ ПОМИЛКА: " + e.getMessage());

        } catch (IOException e) {
            logger.error("Помилка IO при читанні клієнтів", e);
            System.err.println("❌ ПОМИЛКА: " + e.getMessage());

        } finally {
            logger.info("Завершення завантаження клієнтів");
        }
    }

    private static void demonstrateErrorHandling() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. ДЕМОНСТРАЦІЯ ОБРОБКИ ПОМИЛОК");
        System.out.println("=".repeat(80));

        // Тест 1: Неіснуючий файл
        System.out.println("\n--- Тест 1: Неіснуючий файл ---");
        testNonExistentFile();

        // Тест 2: Невалідні дані
        System.out.println("\n--- Тест 2: Невалідні дані ---");
        testInvalidData();

        // Тест 3: Порожній файл
        System.out.println("\n--- Тест 3: Власне виключення ---");
        testCustomException();
    }

    private static void testNonExistentFile() {
        try {
            logger.info("Спроба відкрити неіснуючий файл");
            List<String[]> data = com.ecommerce.service.FileReader.readCSV("data/nonexistent.csv");

        } catch (FileNotFoundException e) {
            logger.warning("Очікувана помилка: файл не знайдено");
            System.out.println("✓ FileNotFoundException перехоплено: " + e.getMessage());

        } catch (IOException | InvalidDataException e) {
            logger.error("Неочікувана помилка", e);
            System.err.println("❌ Неочікувана помилка: " + e.getMessage());
        }
    }

    private static void testInvalidData() {
        try {
            logger.info("Спроба створити товар з невалідними даними");

            // Спроба створити товар з від'ємною ціною
            String[] invalidData = {"Товар", "-100", "5", "ELECTRONICS", "2024-10-01"};
            throw new InvalidDataException(
                    "Ціна не може бути від'ємною",
                    "price",
                    invalidData[1],
                    InvalidDataException.ErrorCode.NEGATIVE_VALUE
            );

        } catch (InvalidDataException e) {
            logger.warning("Очікувана помилка: невалідні дані");
            System.out.println("✓ InvalidDataException перехоплено:");
            System.out.println("  " + e.getDetailedMessage());
        }
    }

    private static void testCustomException() {
        try {
            logger.info("Тестування власного виключення");

            throw new InvalidDataException(
                    "Тестова помилка валідації",
                    "testField",
                    "invalidValue",
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );

        } catch (InvalidDataException e) {
            logger.warning("Власне виключення перехоплено");
            System.out.println("✓ Власне виключення спрацювало:");
            System.out.println("  Код помилки: " + e.getErrorCode());
            System.out.println("  Поле: " + e.getFieldName());
            System.out.println("  Значення: " + e.getInvalidValue());
            System.out.println("  Повідомлення: " + e.getMessage());
        }
    }

    private static void demonstrateMultiCatch() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. ДЕМОНСТРАЦІЯ MULTI-CATCH");
        System.out.println("=".repeat(80));

        String[] testFiles = {
                "data/products.csv",
                "data/nonexistent.csv",
                "data/invalid_products.csv"
        };

        for (String file : testFiles) {
            System.out.println("\n--- Обробка файлу: " + file + " ---");

            try {
                logger.info("Спроба читання: " + file);
                List<String[]> data = com.ecommerce.service.FileReader.readCSV(file);
                System.out.println("✓ Файл прочитано успішно: " + data.size() + " записів");

            } catch (FileNotFoundException | InvalidDataException e) {
                // Multi-catch для схожих типів помилок
                logger.warning("Помилка файлу або даних: " + e.getMessage());
                System.out.println("⚠ Помилка: " + e.getClass().getSimpleName() + " - " + e.getMessage());

            } catch (IOException e) {
                logger.error("Помилка вводу/виводу", e);
                System.err.println("❌ IO помилка: " + e.getMessage());

            } catch (Exception e) {
                logger.error("Неочікувана помилка", e);
                System.err.println("❌ Неочікувана помилка: " + e.getMessage());
            }
        }
    }

    private static void demonstrateTryWithResources() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. ДЕМОНСТРАЦІЯ TRY-WITH-RESOURCES");
        System.out.println("=".repeat(80));

        String filePath = "data/products.csv";

        // Try-with-resources автоматично закриває ресурси
        try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(
                java.nio.file.Paths.get(filePath))) {

            logger.info("Файл відкрито з try-with-resources");
            String line = reader.readLine();
            System.out.println("✓ Перший рядок: " + line);
            System.out.println("✓ BufferedReader автоматично закриється");

        } catch (IOException e) {
            logger.error("Помилка при роботі з try-with-resources", e);
            System.err.println("❌ Помилка: " + e.getMessage());
        }
        // Ресурс (reader) автоматично закривається тут, навіть якщо виникла помилка

        logger.info("Try-with-resources завершено, ресурси закриті");
    }
}
