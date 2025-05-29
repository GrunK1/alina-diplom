package com.example.alinadiplom.model;

public enum Priority {
    CRITICAL("Критический"),
    HIGH("Высокий"),
    MEDIUM_PLUS("Средний+"),
    MEDIUM("Средний"),
    LOW("Низкий");

    private final String title;

    Priority(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public static Priority getPriority(int days) {
        if (days <= 6) return Priority.LOW;
        else if (days <= 14) return Priority.MEDIUM;
        else if (days <= 28) return Priority.MEDIUM_PLUS;
        else if (days <= 45) return Priority.HIGH;
        else return Priority.CRITICAL;
    }

    public static Priority getPriority(String title) {
        for (Priority p : values()) {
            if (p.getTitle().equalsIgnoreCase(title)) { // Используем equalsIgnoreCase для надёжности
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown priority title: " + title);
    }

}
