package com.cen4072.support;

/**
 * Single canonical OCW course used for deep-link tests.
 * <p>
 * Spring 2020 6.006 is a current, well-structured offering with predictable {@code /pages/} and
 * {@code /resources/} URLs. See
 * <a href="https://ocw.mit.edu/courses/6-006-introduction-to-algorithms-spring-2020/">the course home</a>.
 */
public final class OcwSampleCourse {

    public static final String PATH =
            "courses/6-006-introduction-to-algorithms-spring-2020/";

    /** Resource page that should expose lecture media (iframe/video) for smoke tests. */
    public static final String LECTURE_WITH_MEDIA =
            PATH + "resources/lecture-1-algorithms-and-computation/";

    /**
     * Stable, individually addressable PDF resource slug (Problem Set 0 Questions).
     * Used to validate that course "/resources/{slug}/" pages load, expose metadata
     * (h1, "Problem Set", etc.), and are a legitimate downloadable artifact.
     */
    public static final String PDF_RESOURCE =
            PATH + "resources/mit6_006s20_ps0-questions/";

    private OcwSampleCourse() {
    }
}
