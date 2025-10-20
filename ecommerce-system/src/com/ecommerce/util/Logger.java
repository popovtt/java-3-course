package com.ecommerce.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "logs/application.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static Logger instance;

    public enum Level {
        INFO("INFO"),
        WARNING("WARN"),
        ERROR("ERROR"),
        DEBUG("DEBUG");

        private final String label;

        Level(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private Logger() {
        try {
            java.io.File logsDir = new java.io.File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Не вдалося створити директорію для логів: " + e.getMessage());
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    private void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logMessage = String.format("[%s] [%s] %s", timestamp, level.getLabel(), message);

        System.out.println(logMessage);

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            System.err.println("Помилка запису в лог-файл: " + e.getMessage());
        }
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message + " | Exception: " + throwable.getMessage());
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }
}
