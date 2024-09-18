package rut.miit.repairservice.models.enums;

public enum StatusType {
    PROCESSING(1),   // Обрабатывается
    ACCEPTED(2),     // Принят
    CANCELLED(3),    // Отменен
    READY(4);        // Готов

    private final int statusCode;

    private StatusType(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

