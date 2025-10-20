package com.ecommerce;

import com.ecommerce.model.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("ДЕМОНСТРАЦІЯ E-COMMERCE СИСТЕМИ");
        System.out.println("=".repeat(80));

        // Демонстрація 1: Створення об'єктів різними способами
        demonstrateObjectCreation();

        // Демонстрація 2: Робота валідації
        demonstrateValidation();

        // Демонстрація 3: Використання toString, equals, hashCode
        demonstrateObjectMethods();

        // Демонстрація 4: Робота з protected та package-private
        demonstrateAccessModifiers();

        // Демонстрація 5: Повний сценарій використання
        demonstrateFullScenario();
    }

    private static void demonstrateObjectCreation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. СТВОРЕННЯ ОБ'ЄКТІВ РІЗНИМИ СПОСОБАМИ");
        System.out.println("=".repeat(80));

        // Через конструктор
        Product laptop = new Product("Ноутбук Dell XPS 15", 45000, 10);
        System.out.println("Створено через конструктор: " + laptop);

        // Через factory-метод
        Product phone = Product.createProduct("iPhone 15 Pro", 35000, 5);
        System.out.println("Створено через factory-метод: " + phone);

        // Через factory-метод з датою
        Product tablet = Product.createProductWithDate("iPad Air", 25000, 8,
                LocalDate.of(2024, 10, 1));
        System.out.println("Створено з датою: " + tablet);

        // Через factory-метод за замовчуванням
        Product defaultProduct = Product.createDefaultProduct();
        System.out.println("Створено за замовчуванням: " + defaultProduct);

        // Створення Customer
        Customer customer1 = new Customer("Іван", "Петренко", "ivan@example.com");
        Customer customer2 = Customer.createCustomer("Марія", "Коваленко", "maria@example.com");
        Customer guest = Customer.createGuestCustomer();

        System.out.println("\nКлієнти:");
        System.out.println("- " + customer1);
        System.out.println("- " + customer2);
        System.out.println("- " + guest);
    }

    private static void demonstrateValidation() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. ДЕМОНСТРАЦІЯ ВАЛІДАЦІЇ");
        System.out.println("=".repeat(80));

        // Успішна валідація
        System.out.println("\n--- Успішна валідація ---");
        try {
            Product validProduct = new Product("Мишка Logitech", 800, 50);
            System.out.println("✓ Товар створено успішно: " + validProduct.getName());

            Customer validCustomer = new Customer("Олег", "Сидоренко", "oleg@test.com");
            System.out.println("✓ Клієнт створено успішно: " + validCustomer.getFullName());

            Review validReview = new Review(validProduct, validCustomer, 5, "Чудовий товар!");
            System.out.println("✓ Відгук створено успішно: " + validReview.getRatingStars());

        } catch (Exception e) {
            System.out.println("✗ Помилка: " + e.getMessage());
        }

        // Неуспішна валідація
        System.out.println("\n--- Неуспішна валідація ---");

        // Спроба створити товар з невалідною ціною
        System.out.println("\nСпроба 1: Невалідна ціна");
        try {
            Product invalidProduct = new Product("Товар", -100, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Помилка перехоплена: " + e.getMessage());
        }

        // Спроба створити клієнта з невалідним email
        System.out.println("\nСпроба 2: Невалідний email");
        try {
            Customer invalidCustomer = new Customer("Тест", "Тестовий", "invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Помилка перехоплена: " + e.getMessage());
        }

        // Спроба створити відгук з невалідним рейтингом
        System.out.println("\nСпроба 3: Невалідний рейтинг");
        try {
            Product product = Product.createProduct("Товар", 100, 5);
            Customer customer = Customer.createCustomer("Ім'я", "Прізвище", "test@test.com");
            Review invalidReview = new Review(product, customer, 10, "Коментар");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Помилка перехоплена: " + e.getMessage());
        }

        // Спроба створити пусте замовлення
        System.out.println("\nСпроба 4: Пусте замовлення");
        try {
            Customer customer = Customer.createCustomer("Тест", "Тестовий", "test@test.com");
            Order emptyOrder = new Order(customer, Arrays.asList());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Помилка перехоплена: " + e.getMessage());
        }
    }

    private static void demonstrateObjectMethods() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. ДЕМОНСТРАЦІЯ toString(), equals(), hashCode()");
        System.out.println("=".repeat(80));

        // toString()
        System.out.println("\n--- Метод toString() ---");
        Product product1 = Product.createProduct("Клавіатура", 1200, 15);
        Customer customer1 = Customer.createCustomer("Петро", "Іваненко", "petro@test.com");

        System.out.println("Product: " + product1);
        System.out.println("Customer: " + customer1);

        // equals() та hashCode()
        System.out.println("\n--- Методи equals() та hashCode() ---");
        Product product2 = Product.createProduct("Клавіатура", 1200, 15);
        Product product3 = Product.createProduct("Миша", 800, 20);

        System.out.println("product1.equals(product2): " + product1.equals(product2));
        System.out.println("product1.equals(product3): " + product1.equals(product3));

        System.out.println("\nHashCode product1: " + product1.hashCode());
        System.out.println("HashCode product2: " + product2.hashCode());
        System.out.println("HashCode product3: " + product3.hashCode());

        if (product1.equals(product2)) {
            System.out.println("\n✓ Контракт equals/hashCode виконано: " +
                    "однакові об'єкти мають однаковий hashCode");
        }
    }

    private static void demonstrateAccessModifiers() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. ДЕМОНСТРАЦІЯ МОДИФІКАТОРІВ ДОСТУПУ");
        System.out.println("=".repeat(80));

        // Public - доступні звідусіль
        System.out.println("\n--- Public модифікатори ---");
        Product product = new Product("Монітор Samsung", 8000, 7);
        System.out.println("✓ Доступ до public методів:");
        System.out.println("  - getName(): " + product.getName());
        System.out.println("  - getPrice(): " + product.getPrice());
        System.out.println("  - isInStock(): " + product.isInStock());

        // Protected - доступні в підкласах
        System.out.println("\n--- Protected модифікатори ---");
        System.out.println("✓ Protected поля (createdDate) доступні через getter:");
        System.out.println("  - getCreatedDate(): " + product.getCreatedDate());

        // Package-private - ValidationHelper використовується тільки в Utils
        System.out.println("\n--- Package-private модифікатори ---");
        System.out.println("✓ ValidationHelper (package-private) використовується через Utils:");
        try {
            ua.util.Utils.validateEmail("valid@email.com");
            System.out.println("  - Email валідація через Utils: SUCCESS");
        } catch (Exception e) {
            System.out.println("  - Помилка: " + e.getMessage());
        }

        // Private - недоступні ззовні
        System.out.println("\n--- Private модифікатори ---");
        System.out.println("✓ Private поля доступні тільки через getter/setter:");
        System.out.println("  - Прямий доступ до полів НЕМОЖЛИВИЙ");
        System.out.println("  - Доступ тільки через public методи");
    }

    private static void demonstrateFullScenario() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. ПОВНИЙ СЦЕНАРІЙ ВИКОРИСТАННЯ");
        System.out.println("=".repeat(80));

        // Крок 1: Створення товарів
        System.out.println("\n--- Крок 1: Створення каталогу товарів ---");
        Product laptop = Product.createProduct("Ноутбук HP Pavilion", 28000, 3);
        Product mouse = Product.createProduct("Миша Razer", 1500, 25);
        Product keyboard = Product.createProduct("Клавіатура Logitech", 2200, 15);

        System.out.println("Каталог товарів:");
        System.out.println("1. " + laptop);
        System.out.println("2. " + mouse);
        System.out.println("3. " + keyboard);

        // Крок 2: Реєстрація клієнтів
        System.out.println("\n--- Крок 2: Реєстрація клієнтів ---");
        Customer customer1 = Customer.createCustomer("Анна", "Шевченко", "anna@example.com");
        Customer customer2 = Customer.createCustomerWithDate("Дмитро", "Бондаренко",
                "dmytro@example.com", LocalDate.of(2024, 9, 15));

        System.out.println("Зареєстровані клієнти:");
        System.out.println("1. " + customer1);
        System.out.println("2. " + customer2);

        // Крок 3: Створення замовлення
        System.out.println("\n--- Крок 3: Створення замовлення ---");
        List<Product> orderProducts = Arrays.asList(laptop, mouse, keyboard);
        Order order1 = Order.createOrder(customer1, orderProducts);

        System.out.println("Створено замовлення:");
        System.out.println(order1);
        System.out.println("Товари в замовленні:");
        order1.getProducts().forEach(p -> System.out.println("  - " + p.getName() +
                " (" + ua.util.Utils.formatPrice(p.getPrice()) + ")"));

        // Крок 4: Створення відгуку
        System.out.println("\n--- Крок 4: Створення відгуків ---");
        Review review1 = Review.createReview(laptop, customer1, 5,
                "Чудовий ноутбук! Швидкий і надійний.");
        Review review2 = Review.createQuickReview(mouse, customer2, 4);

        System.out.println("Відгуки клієнтів:");
        System.out.println("1. " + review1);
        System.out.println("2. " + review2);

        // Крок 5: Створення відправки
        System.out.println("\n--- Крок 5: Створення відправки ---");
        Shipment shipment1 = Shipment.createShipment(order1);
        Shipment shipment2 = Shipment.createPendingShipment(order1);

        System.out.println("Відправки:");
        System.out.println("1. " + shipment1);
        System.out.println("2. " + shipment2);

        // Крок 6: Оновлення статусу
        System.out.println("\n--- Крок 6: Оновлення статусу відправки ---");
        System.out.println("До оновлення: " + shipment2);
        shipment2.markAsShipped();
        System.out.println("Після оновлення: " + shipment2);

        // Крок 7: Робота зі складом
        System.out.println("\n--- Крок 7: Управління складом ---");
        System.out.println("Залишок ноутбуків до продажу: " + laptop.getStock());
        try {
            laptop.decreaseStock(2);
            System.out.println("✓ Продано 2 ноутбуки");
            System.out.println("Залишок ноутбуків після продажу: " + laptop.getStock());
        } catch (Exception e) {
            System.out.println("✗ Помилка: " + e.getMessage());
        }

        // Спроба продати більше, ніж є на складі
        System.out.println("\nСпроба продати 5 ноутбуків (доступно: " + laptop.getStock() + "):");
        try {
            laptop.decreaseStock(5);
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Помилка перехоплена: " + e.getMessage());
        }

        // Фінальна статистика
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ФІНАЛЬНА СТАТИСТИКА");
        System.out.println("=".repeat(80));
        System.out.println("Всього клієнтів: 2");
        System.out.println("Всього замовлень: 1");
        System.out.println("Всього відгуків: 2");
        System.out.println("Всього відправок: 2");
        System.out.println("Загальна сума замовлення: " +
                ua.util.Utils.formatPrice(order1.getTotalAmount()));
        System.out.println("=".repeat(80));
    }
}
