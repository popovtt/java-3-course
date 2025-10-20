package com.ecommerce;

import com.ecommerce.enums.*;
import com.ecommerce.model.*;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("ДЕМОНСТРАЦІЯ E-COMMERCE SYSTEM - ЛР 2");
        System.out.println("Enum, Record та Switch-case");
        System.out.println("=".repeat(80));

        demonstrateEnums();
        demonstrateRecords();
        demonstrateSwitchExpressions();
        demonstrateFullScenario();
    }

    private static void demonstrateEnums() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. ДЕМОНСТРАЦІЯ ENUM");
        System.out.println("=".repeat(80));

        // OrderStatus
        System.out.println("\n--- OrderStatus ---");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(status);
            System.out.println("  • Активний: " + status.isActive());
            System.out.println("  • Можна скасувати: " + status.canCancel());
            System.out.println("  • Очікувані дні: " + status.getEstimatedDays());
        }

        // ProductCategory
        System.out.println("\n--- ProductCategory ---");
        for (ProductCategory category : ProductCategory.values()) {
            System.out.println(category);
            System.out.println("  • Ціна з податком (1000 грн): " +
                    category.getPriceWithTax(1000.0) + " грн");
            System.out.println("  • Можна повернути: " + category.isReturnable());
            System.out.println("  • Днів на повернення: " + category.getReturnDays());
        }

        // PaymentMethod
        System.out.println("\n--- PaymentMethod ---");
        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.println(method);
            System.out.println("  • Комісія з 1000 грн: " +
                    method.calculateCommission(1000.0) + " грн");
            System.out.println("  • Потребує верифікації: " + method.requiresVerification());
        }

        // ShipmentStatus
        System.out.println("\n--- ShipmentStatus ---");
        for (ShipmentStatus status : ShipmentStatus.values()) {
            System.out.println(status);
            System.out.println("  • В процесі: " + status.isInProgress());
            System.out.println("  • Завершено: " + status.isCompleted());
        }
    }

    private static void demonstrateRecords() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. ДЕМОНСТРАЦІЯ RECORD");
        System.out.println("=".repeat(80));

        // ProductInfo
        System.out.println("\n--- ProductInfo ---");
        ProductInfo laptop = ProductInfo.createProduct(
                "Ноутбук Dell XPS 15",
                45000,
                10,
                ProductCategory.ELECTRONICS
        );
        System.out.println(laptop);
        System.out.println("  • Ціна з податком: " + laptop.getPriceWithTax() + " грн");
        System.out.println("  • Податок: " + laptop.getTaxAmount() + " грн");
        System.out.println("  • Статус: " + laptop.getStockStatus());

        // Незмінність record
        System.out.println("\n--- Незмінність Record ---");
        System.out.println("Початковий stock: " + laptop.stock());
        ProductInfo laptopUpdated = laptop.decreaseStock(3);
        System.out.println("Після decreaseStock(3):");
        System.out.println("  • Оригінальний stock: " + laptop.stock());
        System.out.println("  • Новий об'єкт stock: " + laptopUpdated.stock());

        // CustomerInfo
        System.out.println("\n--- CustomerInfo ---");
        CustomerInfo customer = CustomerInfo.createCustomer(
                "Іван",
                "Петренко",
                "ivan@example.com",
                "+380501234567"
        );
        System.out.println(customer);
        System.out.println("  • Статус: " + customer.getMembershipStatus());
        System.out.println("  • Знижка: " + (customer.getDiscount() * 100) + "%");

        CustomerInfo customerUpgraded = customer.upgradeTier();
        System.out.println("\nПісля upgradeTier():");
        System.out.println("  • Оригінальний tier: " + customer.tier());
        System.out.println("  • Новий tier: " + customerUpgraded.tier());

        // OrderInfo
        System.out.println("\n--- OrderInfo ---");
        ProductInfo phone = ProductInfo.createProduct(
                "iPhone 15",
                35000,
                5,
                ProductCategory.ELECTRONICS
        );

        OrderInfo order = OrderInfo.createOrder(
                customer,
                List.of(laptop, phone),
                PaymentMethod.CREDIT_CARD
        );

        System.out.println(order);
        System.out.println("  • Підсумок: " + order.getSubtotal() + " грн");
        System.out.println("  • Податки: " + order.getTaxTotal() + " грн");
        System.out.println("  • Знижка: " + order.getDiscount() + " грн");
        System.out.println("  • Комісія: " + order.getPaymentCommission() + " грн");
        System.out.println("  • Разом: " + order.getTotalAmount() + " грн");
    }

    private static void demonstrateSwitchExpressions() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. ДЕМОНСТРАЦІЯ SWITCH EXPRESSIONS");
        System.out.println("=".repeat(80));

        // Switch з OrderStatus
        System.out.println("\n--- Switch з OrderStatus ---");
        OrderStatus[] statuses = OrderStatus.values();

        for (OrderStatus status : statuses) {
            String message = switch (status) {
                case PENDING -> "⏳ Замовлення очікує обробки. Будь ласка, зачекайте.";
                case CONFIRMED -> "✅ Ваше замовлення підтверджено!";
                case PROCESSING -> "📦 Ми обробляємо ваше замовлення.";
                case SHIPPED -> "🚚 Замовлення в дорозі до вас!";
                case DELIVERED -> "✓ Замовлення успішно доставлене!";
                case CANCELLED -> "❌ На жаль, замовлення скасовано.";
            };
            System.out.println(status.getUkrainianName() + ": " + message);
        }

        // Switch з ProductCategory та обчисленнями
        System.out.println("\n--- Switch з ProductCategory та обчисленнями ---");
        ProductCategory[] categories = ProductCategory.values();
        double basePrice = 1000.0;

        for (ProductCategory category : categories) {
            String warranty = switch (category) {
                case ELECTRONICS -> "2 роки гарантії виробника";
                case FURNITURE -> "1 рік гарантії";
                case CLOTHING, BOOKS, FOOD, TOYS, SPORTS, BEAUTY ->
                        category.getReturnDays() + " днів на повернення";
            };

            System.out.printf("%-15s: %.2f грн → %.2f грн з податком | %s%n",
                    category.getUkrainianName(),
                    basePrice,
                    category.getPriceWithTax(basePrice),
                    warranty
            );
        }

        // Switch з PaymentMethod
        System.out.println("\n--- Switch з PaymentMethod ---");
        PaymentMethod[] methods = PaymentMethod.values();
        double amount = 5000.0;

        for (PaymentMethod method : methods) {
            String recommendation = switch (method) {
                case CASH -> "💵 Найшвидший спосіб, без комісії";
                case CREDIT_CARD, DEBIT_CARD -> "💳 Швидко та зручно";
                case PAYPAL -> "🌐 Безпечна онлайн-оплата";
                case BANK_TRANSFER -> "🏦 Для великих сум";
                case CRYPTO -> "₿ Сучасний спосіб оплати";
            };

            System.out.printf("%-20s: Комісія %.2f грн | %s%n",
                    method.getUkrainianName(),
                    method.calculateCommission(amount),
                    recommendation
            );
        }

        System.out.println("\n--- Switch з умовами (stock levels) ---");
        ProductInfo[] products = {
                ProductInfo.createProduct("Товар A", 100, 0, ProductCategory.ELECTRONICS),
                ProductInfo.createProduct("Товар B", 200, 3, ProductCategory.CLOTHING),
                ProductInfo.createProduct("Товар C", 300, 15, ProductCategory.BOOKS),
                ProductInfo.createProduct("Товар D", 400, 50, ProductCategory.FURNITURE)
        };

        for (ProductInfo product : products) {
            String action;
            int s = product.stock();
            if (s == 0) {
                action = "🔴 ТЕРМІНОВО: Замовити новий товар!";
            } else if (s > 0 && s <= 5) {
                action = "🟡 УВАГА: Залишилось мало, поповнити запас";
            } else if (s > 5 && s <= 20) {
                action = "🟢 Нормальний рівень запасів";
            } else {
                action = "🟢🟢 Достатньо товару на складі";
            }

            System.out.printf("%-10s (Stock: %2d): %s%n",
                    product.name(), product.stock(), action);
        }

        // Switch з ShipmentStatus та progress
        System.out.println("\n--- Switch з ShipmentStatus та прогрес ---");
        ShipmentStatus[] shipmentStatuses = ShipmentStatus.values();

        for (ShipmentStatus status : shipmentStatuses) {
            String progressBar = switch (status.getProgressPercentage()) {
                case 0 -> "□□□□□ 0%";
                case 20 -> "■□□□□ 20%";
                case 50 -> "■■■□□ 50%";
                case 80 -> "■■■■□ 80%";
                case 100 -> "■■■■■ 100%";
                default -> "-----";
            };

            System.out.printf("%-20s: %s%n", status.getUkrainianName(), progressBar);
        }
    }

    private static void demonstrateFullScenario() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. ПОВНИЙ СЦЕНАРІЙ ВИКОРИСТАННЯ");
        System.out.println("=".repeat(80));

        // Крок 1: Створення товарів
        System.out.println("\n--- Крок 1: Створення каталогу товарів ---");
        ProductInfo laptop = ProductInfo.createProduct(
                "Ноутбук HP Pavilion", 28000, 15, ProductCategory.ELECTRONICS);
        ProductInfo phone = ProductInfo.createProduct(
                "Samsung Galaxy S24", 25000, 8, ProductCategory.ELECTRONICS);
        ProductInfo book = ProductInfo.createProduct(
                "Java Programming", 500, 30, ProductCategory.BOOKS);

        List<ProductInfo> catalog = List.of(laptop, phone, book);
        System.out.println("Каталог товарів:");
        catalog.forEach(p -> System.out.println("  • " + p));

        // Крок 2: Реєстрація клієнтів
        System.out.println("\n--- Крок 2: Реєстрація клієнтів ---");
        CustomerInfo customer1 = CustomerInfo.createCustomer(
                "Олександр", "Шевченко", "alex@example.com", "+380501111111");
        CustomerInfo customer2 = CustomerInfo.createVIPCustomer(
                "Марія", "Коваленко", "maria@example.com", "+380502222222");

        System.out.println("Зареєстровані клієнти:");
        System.out.println("  • " + customer1);
        System.out.println("  • " + customer2);

        // Крок 3: Створення замовлення
        System.out.println("\n--- Крок 3: Створення замовлення ---");
        OrderInfo order1 = OrderInfo.createOrder(
                customer1,
                List.of(laptop, book),
                PaymentMethod.CREDIT_CARD
        );

        System.out.println("Замовлення створено:");
        System.out.println(order1);
        System.out.println("\nДеталі замовлення:");
        System.out.println("  • Товарів: " + order1.getProductCount());
        System.out.println("  • Підсумок: " + order1.getSubtotal() + " грн");
        System.out.println("  • Податки: " + order1.getTaxTotal() + " грн");
        System.out.println("  • Знижка клієнта: " + order1.getDiscount() + " грн");
        System.out.println("  • Комісія: " + order1.getPaymentCommission() + " грн");
        System.out.println("  • Разом до сплати: " + order1.getTotalAmount() + " грн");

        // Крок 4: Життєвий цикл замовлення
        System.out.println("\n--- Крок 4: Життєвий цикл замовлення ---");
        System.out.println("Статус 1: " + order1.status() + " - " + order1.getOrderSummary());

        OrderInfo order2 = order1.withStatus(OrderStatus.CONFIRMED);
        System.out.println("Статус 2: " + order2.status() + " - " + order2.getOrderSummary());

        OrderInfo order3 = order2.withStatus(OrderStatus.PROCESSING);
        System.out.println("Статус 3: " + order3.status() + " - " + order3.getOrderSummary());

        OrderInfo order4 = order3.withStatus(OrderStatus.SHIPPED);
        System.out.println("Статус 4: " + order4.status() + " - " + order4.getOrderSummary());

        OrderInfo order5 = order4.withStatus(OrderStatus.DELIVERED);
        System.out.println("Статус 5: " + order5.status() + " - " + order5.getOrderSummary());

        // Крок 5: Управління запасами
        System.out.println("\n--- Крок 5: Управління запасами ---");
        System.out.println("Початковий запас ноутбуків: " + laptop.stock());
        System.out.println("Статус: " + laptop.getStockStatus());

        ProductInfo laptopUpdated = laptop.decreaseStock(12);
        System.out.println("\nПісля продажу 12 одиниць:");
        System.out.println("  • Залишок: " + laptopUpdated.stock());
        System.out.println("  • Статус: " + laptopUpdated.getStockStatus());

        // Крок 6: Порівняння методів оплати
        System.out.println("\n--- Крок 6: Порівняння методів оплати ---");
        double orderAmount = order1.getTotalAmount();
        System.out.println("Сума замовлення: " + orderAmount + " грн\n");

        for (PaymentMethod method : PaymentMethod.values()) {
            double commission = method.calculateCommission(orderAmount);
            double total = method.getTotalAmount(orderAmount);
            System.out.printf("%-20s: комісія %.2f грн, разом %.2f грн (%s)%n",
                    method.getUkrainianName(), commission, total, method.getProcessingTime());
        }

        // Фінальна статистика
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ФІНАЛЬНА СТАТИСТИКА");
        System.out.println("=".repeat(80));

        System.out.println("Всього клієнтів: 2");
        System.out.println("Всього замовлень: 1");
        System.out.println("Всього товарів у каталозі: " + catalog.size());
        System.out.printf("Загальний дохід: %.2f грн%n", order5.getTotalAmount());
        System.out.println("Статус останнього замовлення: " + order5.status().getUkrainianName());

        System.out.println("\n" + "=".repeat(80));
        System.out.println("✓ Демонстрація завершена успішно!");
        System.out.println("=".repeat(80));
    }
}
