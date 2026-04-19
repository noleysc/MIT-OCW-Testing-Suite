package com.cen4072.support;

import org.testng.Reporter;

/**
 * Console and TestNG report lines for traceability during suite runs.
 * <p>
 * Every {@code driver.get()} can flood the console; navigation tracing is opt-in via
 * {@code -Docw.trace.nav=true}. Assertion outcomes from tests always log as before.
 */
public final class TestOutput {

    private static final boolean TRACE_NAV =
            Boolean.parseBoolean(System.getProperty("ocw.trace.nav", "false"));

    private TestOutput() {
    }

    /** Verbose navigation / URL lines (off unless {@code -Docw.trace.nav=true}). */
    public static boolean traceNav() {
        return TRACE_NAV;
    }

    public static void step(String message) {
        String line = "[STEP] " + message;
        Reporter.log(line, false);
        System.out.println(line);
    }

    public static void outcome(String label, Object value) {
        String line = "[OUTCOME] " + label + ": " + value;
        Reporter.log(line, false);
        System.out.println(line);
    }
}
