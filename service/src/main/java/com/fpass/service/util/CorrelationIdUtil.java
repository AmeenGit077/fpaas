package com.fpass.service.util;

import java.util.UUID;

public class CorrelationIdUtil {

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();

    public static String getCorrelationId() {
        if (correlationId.get() == null) {
            correlationId.set(UUID.randomUUID().toString());
        }
        return correlationId.get();
    }

    public static void setCorrelationId(String id) {
        correlationId.set(id);
    }

    public static void clear() {
        correlationId.remove();
    }
}
