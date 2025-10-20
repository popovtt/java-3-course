package com.ecommerce.service;

import com.ecommerce.enums.ProductCategory;
import com.ecommerce.exceptions.InvalidDataException;
import com.ecommerce.model.ProductInfo;
import com.ecommerce.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервіс для роботи з товарами
 */
public class ProductService {

    private static final Logger logger = Logger.getInstance();
    private static final String PRODUCTS_FILE = "data/products.csv";

    /**
     * Завантажує товари з CSV файлу
     * Формат: name,price,stock,category,createdDate
     */
    public List<ProductInfo> loadProducts() throws FileNotFoundException, IOException, InvalidDataException {
        logger.info("Початок завантаження товарів з файлу");

        List<ProductInfo> products = new ArrayList<>();
        List<String[]> records;

        try {
            records = FileReader.readCSV(PRODUCTS_FILE);
        } catch (FileNotFoundException e) {
            logger.error("Файл товарів не знайдено: " + PRODUCTS_FILE);
            throw e;
        } catch (IOException e) {
            logger.error("Помилка читання файлу товарів", e);
            throw e;
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < records.size(); i++) {
            String[] values = records.get(i);

            try {
                ProductInfo product = parseProduct(values, i + 2); // +2 (заголовок + 1-based)
                products.add(product);
                successCount++;
                logger.info("Товар створено: " + product.name());

            } catch (InvalidDataException e) {
                failCount++;
                logger.error("Не вдалося створити товар з рядка #" + (i + 2) + ": " + e.getMessage());
                // Продовжуємо обробку інших записів
            }
        }

        logger.info(String.format("Завантаження завершено: успішно=%d, помилок=%d", successCount, failCount));

        if (products.isEmpty() && !records.isEmpty()) {
            throw new InvalidDataException(
                    "Не вдалося завантажити жодного товару",
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        }

        return products;
    }

    /**
     * Парсить рядок CSV в ProductInfo
     */
    private ProductInfo parseProduct(String[] values, int lineNumber) throws InvalidDataException {
        if (values.length < 5) {
            throw new InvalidDataException(
                    "Недостатньо полів у рядку #" + lineNumber + ". Очікується: 5, отримано: " + values.length,
                    InvalidDataException.ErrorCode.MISSING_REQUIRED_FIELD
            );
        }

        try {
            String name = values[0];
            if (name == null || name.trim().isEmpty()) {
                throw new InvalidDataException(
                        "Порожня назва товару в рядку #" + lineNumber,
                        "name",
                        name,
                        InvalidDataException.ErrorCode.EMPTY_VALUE
                );
            }

            double price;
            try {
                price = Double.parseDouble(values[1]);
                if (price < 0) {
                    throw new InvalidDataException(
                            "Від'ємна ціна в рядку #" + lineNumber,
                            "price",
                            values[1],
                            InvalidDataException.ErrorCode.NEGATIVE_VALUE
                    );
                }
            } catch (NumberFormatException e) {
                throw new InvalidDataException(
                        "Невірний формат ціни в рядку #" + lineNumber,
                        "price",
                        values[1],
                        InvalidDataException.ErrorCode.INVALID_FORMAT,
                        e
                );
            }

            int stock;
            try {
                stock = Integer.parseInt(values[2]);
                if (stock < 0) {
                    throw new InvalidDataException(
                            "Від'ємна кількість в рядку #" + lineNumber,
                            "stock",
                            values[2],
                            InvalidDataException.ErrorCode.NEGATIVE_VALUE
                    );
                }
            } catch (NumberFormatException e) {
                throw new InvalidDataException(
                        "Невірний формат кількості в рядку #" + lineNumber,
                        "stock",
                        values[2],
                        InvalidDataException.ErrorCode.INVALID_FORMAT,
                        e
                );
            }

            ProductCategory category;
            try {
                category = ProductCategory.valueOf(values[3].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException(
                        "Невірна категорія товару в рядку #" + lineNumber,
                        "category",
                        values[3],
                        InvalidDataException.ErrorCode.INVALID_ENUM_VALUE,
                        e
                );
            }

            LocalDate createdDate;
            try {
                createdDate = LocalDate.parse(values[4]);
            } catch (DateTimeParseException e) {
                throw new InvalidDataException(
                        "Невірний формат дати в рядку #" + lineNumber,
                        "createdDate",
                        values[4],
                        InvalidDataException.ErrorCode.INVALID_FORMAT,
                        e
                );
            }

            return new ProductInfo(name, price, stock, category, createdDate);

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
