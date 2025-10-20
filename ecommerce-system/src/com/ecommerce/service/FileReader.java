package com.ecommerce.service;

import com.ecommerce.exceptions.InvalidDataException;
import com.ecommerce.util.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private static final Logger logger = Logger.getInstance();

    public static List<String[]> readCSV(String filePath) throws FileNotFoundException, IOException, InvalidDataException {
        logger.info("Початок читання файлу: " + filePath);

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.error("Файл не знайдено: " + filePath);
            throw new FileNotFoundException("Файл не знайдено: " + filePath);
        }

        List<String[]> records = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(path);
            String line;
            int lineNumber = 0;

            // Пропускаємо заголовок
            String header = reader.readLine();
            if (header == null) {
                throw new InvalidDataException(
                        "Файл порожній: " + filePath,
                        InvalidDataException.ErrorCode.EMPTY_VALUE
                );
            }
            logger.debug("Заголовок CSV: " + header);
            lineNumber++;

            // Читаємо дані
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    logger.warning("Порожній рядок #" + lineNumber + ", пропущено");
                    continue;
                }

                try {
                    String[] values = line.split(",");

                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }

                    records.add(values);
                    logger.debug("Прочитано рядок #" + lineNumber + ": " + line);

                } catch (Exception e) {
                    logger.warning("Помилка парсингу рядка #" + lineNumber + ": " + e.getMessage());
                    throw new InvalidDataException(
                            "Помилка обробки рядка #" + lineNumber,
                            "line",
                            line,
                            InvalidDataException.ErrorCode.INVALID_FORMAT,
                            e
                    );
                }
            }

            logger.info("Успішно прочитано " + records.size() + " записів з файлу: " + filePath);
            return records;

        } catch (IOException e) {
            logger.error("Помилка читання файлу: " + filePath, e);
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    logger.debug("Файл закрито: " + filePath);
                } catch (IOException e) {
                    logger.error("Помилка закриття файлу: " + filePath, e);
                }
            }
        }
    }

    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}
