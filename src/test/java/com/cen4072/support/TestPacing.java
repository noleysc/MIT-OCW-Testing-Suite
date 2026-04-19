package com.cen4072.support;

/**
 * Short gap between automated tests so recordings (and the live site) can breathe.
 * <p>
 * Default {@value #DEFAULT_MS} ms (~3 s). Override with {@code -Docw.pause.between.tests.ms=500}
 * or set to {@code 0} to disable.
 */
public final class TestPacing {

    private static final int DEFAULT_MS = 3000;

    private TestPacing() {
    }

    public static void pauseBetweenTests() {
        int ms = resolveMillis();
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int resolveMillis() {
        String p = System.getProperty("ocw.pause.between.tests.ms");
        if (p == null || p.isBlank()) {
            return DEFAULT_MS;
        }
        try {
            return Integer.parseInt(p.trim());
        } catch (NumberFormatException e) {
            return DEFAULT_MS;
        }
    }
}
