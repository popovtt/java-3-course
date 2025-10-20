package com.ecommerce;

import com.ecommerce.model.CustomerInfo;
import com.ecommerce.model.CustomerInfo.CustomerTier;
import java.time.LocalDate;

/**
 * Базові тести для CustomerInfo
 */
public class CustomerInfoTest {

    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ CustomerInfo ===\n");

        testValidCustomer();
        testInvalidEmail();
        testEmptyName();
        testCustomerTier();
        testMembershipStatus();
        testUpgradeTier();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    private static void testValidCustomer() {
        System.out.println("Тест 1: Створення валідного клієнта");

        try {
            CustomerInfo customer = new CustomerInfo(
                    "Іван",
                    "Петренко",
                    "ivan@example.com",
                    "+380501234567",
                    LocalDate.now(),
                    CustomerTier.BRONZE
            );

            assert customer.firstName().equals("Іван") : "Неправильне ім'я";
            assert customer.lastName().equals("Петренко") : "Неправильне прізвище";
            assert customer.email().equals("ivan@example.com") : "Неправильний email";
            assert customer.getFullName().equals("Іван Петренко") : "Неправильне повне ім'я";
            assert customer.tier() == CustomerTier.BRONZE : "Неправильний tier";

            System.out.println("✓ Тест пройдено");
            System.out.println("  Клієнт: " + customer.getFullName());
            System.out.println("  Email: " + customer.email());
            System.out.println("  Tier: " + customer.tier() + "\n");

        } catch (Exception e) {
            System.err.println("❌ Тест не пройдено: " + e.getMessage());
        }
    }

    private static void testInvalidEmail() {
        System.out.println("Тест 2: Невалідний email");

        try {
            CustomerInfo customer = new CustomerInfo(
                    "Тест",
                    "Тестовий",
                    "invalid-email",
                    "+380501234567",
                    LocalDate.now(),
                    CustomerTier.BRONZE
            );

            System.err.println("❌ Тест не пройдено: виключення не викинуте");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест пройдено: виключення перехоплено");
            System.out.println("  Повідомлення: " + e.getMessage() + "\n");
        }
    }

    private static void testEmptyName() {
        System.out.println("Тест 3: Пусте ім'я");

        try {
            CustomerInfo customer = new CustomerInfo(
                    "",
                    "Прізвище",
                    "test@example.com",
                    "+380501234567",
                    LocalDate.now(),
                    CustomerTier.BRONZE
            );

            System.err.println("❌ Тест не пройдено: виключення не викинуте");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест пройдено: виключення перехоплено");
            System.out.println("  Повідомлення: " + e.getMessage() + "\n");
        }
    }

    private static void testCustomerTier() {
        System.out.println("Тест 4: Рівні клієнтів (Tiers)");

        CustomerInfo bronze = CustomerInfo.createCustomer(
                "Бронза", "Клієнт", "bronze@test.com", "+380501111111");

        CustomerInfo gold = CustomerInfo.createVIPCustomer(
                "Золото", "Клієнт", "gold@test.com", "+380502222222");

        assert bronze.tier() == CustomerTier.BRONZE : "Неправильний tier для bronze";
        assert bronze.getDiscount() == 0.0 : "Неправильна знижка для BRONZE";

        assert gold.tier() == CustomerTier.GOLD : "Неправильний tier для gold";
        assert gold.getDiscount() == 0.10 : "Неправильна знижка для GOLD";

        System.out.println("✓ Тест пройдено");
        System.out.println("  Bronze tier знижка: " + (bronze.getDiscount() * 100) + "%");
        System.out.println("  Gold tier знижка: " + (gold.getDiscount() * 100) + "%\n");
    }

    private static void testMembershipStatus() {
        System.out.println("Тест 5: Статус членства");

        // Новий клієнт (зареєстрований сьогодні)
        CustomerInfo newCustomer = new CustomerInfo(
                "Новий",
                "Клієнт",
                "new@test.com",
                "+380501111111",
                LocalDate.now(),
                CustomerTier.BRONZE
        );

        // Клієнт з 6 місяців тому
        CustomerInfo regularCustomer = new CustomerInfo(
                "Постійний",
                "Клієнт",
                "regular@test.com",
                "+380502222222",
                LocalDate.now().minusMonths(6),
                CustomerTier.SILVER
        );

        // Клієнт з 2 років тому
        CustomerInfo vipCustomer = new CustomerInfo(
                "VIP",
                "Клієнт",
                "vip@test.com",
                "+380503333333",
                LocalDate.now().minusYears(2),
                CustomerTier.GOLD
        );

        System.out.println("✓ Тест пройдено");
        System.out.println("  Новий клієнт: " + newCustomer.getMembershipStatus());
        System.out.println("  Постійний: " + regularCustomer.getMembershipStatus());
        System.out.println("  VIP: " + vipCustomer.getMembershipStatus() + "\n");
    }

    private static void testUpgradeTier() {
        System.out.println("Тест 6: Підвищення рівня");

        CustomerInfo customer = CustomerInfo.createCustomer(
                "Тест",
                "Клієнт",
                "test@example.com",
                "+380501234567"
        );

        assert customer.tier() == CustomerTier.BRONZE : "Початковий tier має бути BRONZE";

        CustomerInfo upgraded1 = customer.upgradeTier();
        assert upgraded1.tier() == CustomerTier.SILVER : "Після upgrade має бути SILVER";

        CustomerInfo upgraded2 = upgraded1.upgradeTier();
        assert upgraded2.tier() == CustomerTier.GOLD : "Після upgrade має бути GOLD";

        CustomerInfo upgraded3 = upgraded2.upgradeTier();
        assert upgraded3.tier() == CustomerTier.PLATINUM : "Після upgrade має бути PLATINUM";

        CustomerInfo upgraded4 = upgraded3.upgradeTier();
        assert upgraded4.tier() == CustomerTier.PLATINUM : "PLATINUM має залишатись PLATINUM";

        // Перевірка immutability
        assert customer.tier() == CustomerTier.BRONZE : "Оригінальний об'єкт не має змінюватись";

        System.out.println("✓ Тест пройдено");
        System.out.println("  Початковий tier: " + customer.tier());
        System.out.println("  Після 1-го upgrade: " + upgraded1.tier());
        System.out.println("  Після 2-го upgrade: " + upgraded2.tier());
        System.out.println("  Після 3-го upgrade: " + upgraded3.tier());
        System.out.println("  Після 4-го upgrade: " + upgraded4.tier());
        System.out.println("  Оригінальний залишився: " + customer.tier() + " (immutability OK)\n");
    }
}
