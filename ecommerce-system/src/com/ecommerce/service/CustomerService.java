package com.ecommerce.service;

import com.ecommerce.exceptions.InvalidDataException;
import com.ecommerce.model.CustomerInfo;
import com.ecommerce.model.CustomerInfo.CustomerTier;
import com.ecommerce.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервіс для роботи з клієнтами
 */
public class CustomerService {

    private static final Logger logger = Logger.getInstance();
    private static final String CUSTOMERS_FILE = "data/customers.csv";

    /**
     * Завантажує клієнтів з CSV файлу
     * Формат: firstName,lastName,email,phone,registrationDate,tier
     */
    public List<CustomerInfo> loadCustomers() throws FileNotFoundException, IOException, InvalidDataException {
        logger.info("Початок завантаження клієнтів з файлу");

        List<CustomerInfo> customers = new ArrayList<>();
        List<String[]> records;

        try {
            records = FileReader.readCSV(CUSTOMERS_FILE);
        } catch (FileNotFoundException e) {
            logger.error("Файл клієнтів не знайдено: " + CUSTOMERS_FILE);
            throw e;
        } catch (IOException e) {
            logger.error("Помилка читання файлу клієнтів", e);
            throw e;
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < records.size(); i++) {
            String[] values = records.get(i);

            try {
                CustomerInfo customer = parseCustomer(values, i + 2);
                customers.add(customer);
                successCount++;
                logger.info("Клієнт створено: " + customer.getFullName());

            } catch (InvalidDataException e) {
                failCount++;
                logger.error("Не вдалося створити клієнта з рядка #" + (i + 2) + ": " + e.getMessage());
            }
        }

        logger.info(String.format("Завантаження завершено: успішно=%d, помилок=%d", successCount, failCount));

        if (customers.isEmpty() && !records.isEmpty()) {
            throw new InvalidDataException(
                    "Не вдалося завантажити жодного клієнта",
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        }

        return customers;
    }

    /**
     * Парсить рядок CSV в CustomerInfo
     */
    private CustomerInfo parseCustomer(String[] values, int lineNumber) throws InvalidDataException {
        if (values.length < 6) {
            throw new InvalidDataException(
                    "Недостатньо полів у рядку #" + lineNumber + ". Очікується: 6, отримано: " + values.length,
                    InvalidDataException.ErrorCode.MISSING_REQUIRED_FIELD
            );
        }

        try {
            String firstName = values[0];
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new InvalidDataException(
                        "Порожнє ім'я в рядку #" + lineNumber,
                        "firstName",
                        firstName,
                        InvalidDataException.ErrorCode.EMPTY_VALUE
                );
            }

            String lastName = values[1];
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new InvalidDataException(
                        "Порожнє прізвище в рядку #" + lineNumber,
                        "lastName",
                        lastName,
                        InvalidDataException.ErrorCode.EMPTY_VALUE
                );
            }

            String email = values[2];
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new InvalidDataException(
                        "Невалідний email в рядку #" + lineNumber,
                        "email",
                        email,
                        InvalidDataException.ErrorCode.INVALID_EMAIL
                );
            }

            String phone = values[3];
            // Phone може бути null або порожнім

            LocalDate registrationDate;
            try {
                registrationDate = LocalDate.parse(values[4]);
            } catch (DateTimeParseException e) {
                throw new InvalidDataException(
                        "Невірний формат дати в рядку #" + lineNumber,
                        "registrationDate",
                        values[4],
                        InvalidDataException.ErrorCode.INVALID_FORMAT,
                        e
                );
            }

            CustomerTier tier;
            try {
                tier = CustomerTier.valueOf(values[5].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException(
                        "Невірний tier клієнта в рядку #" + lineNumber,
                        "tier",
                        values[5],
                        InvalidDataException.ErrorCode.INVALID_ENUM_VALUE,
                        e
                );
            }

            return new CustomerInfo(firstName, lastName, email, phone, registrationDate, tier);

        } catch (InvalidDataException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException(
                    "Неочікувана помилка при обробці рядка #" + lineNumber,
                    InvalidDataException.ErrorCode.INVALID_FORMAT,
                    e
            );
        }
    }
}
