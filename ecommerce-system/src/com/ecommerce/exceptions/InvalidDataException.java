package com.ecommerce.exceptions;

public class InvalidDataException extends Exception {

    private final String fieldName;
    private final String invalidValue;
    private final ErrorCode errorCode;

    public enum ErrorCode {
        INVALID_FORMAT("Невірний формат даних"),
        MISSING_REQUIRED_FIELD("Відсутнє обов'язкове поле"),
        INVALID_RANGE("Значення поза допустимим діапазоном"),
        INVALID_EMAIL("Невірний формат email"),
        NEGATIVE_VALUE("Від'ємне значення"),
        EMPTY_VALUE("Пусте значення"),
        INVALID_ENUM_VALUE("Невірне значення enum"),
        DUPLICATE_ENTRY("Дублікат запису");

        private final String description;

        ErrorCode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public InvalidDataException(String message, String fieldName, String invalidValue, ErrorCode errorCode) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
        this.errorCode = errorCode;
    }

    public InvalidDataException(String message, String fieldName, String invalidValue,
                                ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
        this.errorCode = errorCode;
    }

    public InvalidDataException(String message, ErrorCode errorCode) {
        this(message, null, null, errorCode);
    }

    public InvalidDataException(String message, ErrorCode errorCode, Throwable cause) {
        this(message, null, null, errorCode, cause);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());

        if (errorCode != null) {
            sb.append(" [").append(errorCode).append(": ").append(errorCode.getDescription()).append("]");
        }

        if (fieldName != null) {
            sb.append(" | Поле: ").append(fieldName);
        }

        if (invalidValue != null) {
            sb.append(" | Значення: '").append(invalidValue).append("'");
        }

        return sb.toString();
    }

    public String getDetailedMessage() {
        return String.format("InvalidDataException: %s%nКод помилки: %s (%s)%nПоле: %s%nЗначення: %s",
                super.getMessage(),
                errorCode,
                errorCode != null ? errorCode.getDescription() : "N/A",
                fieldName != null ? fieldName : "N/A",
                invalidValue != null ? invalidValue : "N/A"
        );
    }
}