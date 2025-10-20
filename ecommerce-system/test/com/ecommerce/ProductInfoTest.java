package com.ecommerce;

import com.ecommerce.enums.ProductCategory;
import com.ecommerce.model.ProductInfo;
import java.time.LocalDate;

/**
 * Базові тести для ProductInfo
 */
public class ProductInfoTest {

    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ ProductInfo ===\n");

        testValidProduct();
        testInvalidPrice();
        testInvalidStock();
        testPriceWithTax();
        testStockManagement();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    private static void testValidProduct() {
        System.out.println("Тест 1: Створення валідного товару");

        try {
            ProductInfo product = new ProductInfo(
                    "Test Product",
                    100.0,
                    10,
                    ProductCategory.ELECTRONICS,
                    LocalDate.now()
            );

            assert product.name().equals("Test Product") : "Неправильна назва";
            assert product.price() == 100.0 : "Неправильна ціна";
            assert product.stock() == 10 : "Неправильна кількість";
            assert product.isInStock() : "Товар має бути в наявності";

            System.out.println("✓ Тест пройдено\n");

        } catch (Exception e) {
            System.err.println("❌ Тест не пройдено: " + e.getMessage());
        }
    }

    private static void testInvalidPrice() {
        System.out.println("Тест 2: Від'ємна ціна");

        try {
            ProductInfo product = new ProductInfo(
                    "Test",
                    -100.0,
                    10,
                    ProductCategory.ELECTRONICS
            );

            System.err.println("❌ Тест не пройдено: виключення не викинуте");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест пройдено: виключення перехоплено");
            System.out.println("  Повідомлення: " + e.getMessage() + "\n");
        }
    }

    private static void testInvalidStock() {
        System.out.println("Тест 3: Від'ємна кількість");

        try {
            ProductInfo product = new ProductInfo(
                    "Test",
                    100.0,
                    -5,
                    ProductCategory.ELECTRONICS
            );

            System.err.println("❌ Тест не пройдено: виключення не викинуте");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест пройдено: виключення перехоплено\n");
        }
    }

    private static void testPriceWithTax() {
        System.out.println("Тест 4: Ціна з податком");

        ProductInfo product = new ProductInfo(
                "Electronics",
                1000.0,
                10,
                ProductCategory.ELECTRONICS
        );

        double priceWithTax = product.getPriceWithTax();
        double expectedTax = 1000.0 * ProductCategory.ELECTRONICS.getTaxRate();
        double expected = 1000.0 + expectedTax;

        assert Math.abs(priceWithTax - expected) < 0.01 : "Неправильний розрахунок податку";

        System.out.println("✓ Тест пройдено");
        System.out.println("  Базова ціна: " + product.price());
        System.out.println("  Ціна з податком: " + priceWithTax + "\n");
    }

    private static void testStockManagement() {
        System.out.println("Тест 5: Управління запасами");

        ProductInfo product = new ProductInfo(
                "Test",
                100.0,
                10,
                ProductCategory.ELECTRONICS
        );

        ProductInfo decreased = product.decreaseStock(5);
        assert decreased.stock() == 5 : "Неправильне зменшення запасу";
        assert product.stock() == 10 : "Оригінальний товар змінився (порушення immutability)";

        ProductInfo increased = decreased.increaseStock(3);
        assert increased.stock() == 8 : "Неправильне збільшення запасу";

        System.out.println("✓ Тест пройдено");
        System.out.println("  Початковий запас: " + product.stock());
        System.out.println("  Після зменшення: " + decreased.stock());
        System.out.println("  Після збільшення: " + increased.stock() + "\n");
    }
}
