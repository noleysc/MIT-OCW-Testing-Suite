# Fixing the Serenity + Cucumber Test Suite (AI-Agent Edition)

This guide is written so you can get the Serenity + Cucumber test suite running **using an AI coding agent** (Cursor Agent, Claude Code, Copilot Chat, ChatGPT with file access, etc.). You do not need to understand Java or Cucumber. You need to:

1. Know where the tests live (Section 1).
2. Paste the prompts in Section 3 into your AI agent, one at a time, and let it apply the edits.
3. Run the commands in Section 4 yourself.
4. Read the voiceover script in Section 5 while the screen recording plays.

Assumptions already true on your machine:
- Java 17 + Maven 3.9+ + Chrome are installed and working.
- You have the repo open in an editor that has an AI agent (Cursor is easiest).
- You are in the project root — the folder containing `pom.xml`.

---

## 1. Where the Serenity + Cucumber tests live

Everything Cucumber-related is under `src/test/`. You do **not** need to touch anything outside these paths.

```
pom.xml                              <- build config; activates Cucumber via the `bdd` profile
src/test/resources/features/         <- Gherkin .feature files (the human-readable scenarios)
    search_filtering.feature
    resource_types.feature
    responsive_design.feature
    user_preferences.feature
src/test/resources/serenity.properties  <- Serenity report settings
src/test/java/com/cen4072/steps/     <- Java step definitions that execute each Gherkin line
    CommonSteps.java                 <- Starts/stops the shared Chrome browser
    SearchFilteringSteps.java
    ResourceTypesSteps.java
    ResponsiveDesignSteps.java
    UserPreferencesSteps.java
src/test/java/com/cen4072/runners/
    CucumberTestSuite.java           <- The entry point Failsafe runs
src/test/java/com/cen4072/support/   <- Shared helpers (Chrome options, logging, sample URLs)
src/test/java/com/cen4072/base/
    BaseTest.java                    <- Used by the TestNG suites only (not Cucumber)
src/test/java/com/cen4072/suites/    <- The TestNG unit-test classes (not Cucumber)
```

Quick mental model:
- **Feature files** (`.feature`) are the English scenarios.
- **Step files** (`*Steps.java`) are the Java that matches each `Given/When/Then` line.
- **`CucumberTestSuite.java`** is what Maven Failsafe launches when you pass `-Pbdd`.
- **`target/site/serenity/index.html`** is the report that gets generated after a run.

If you tell your AI agent "open the Cucumber tests," point it at `src/test/resources/features/` and `src/test/java/com/cen4072/steps/`. That's the entire surface area.

---

## 2. Run the suite first so you see the real failures

Do this **before** asking the AI to fix anything. You want concrete error output to paste into the prompts later.

```bash
mvn -Pbdd clean verify > run.log 2>&1
```

Wait for it to finish (5–10 minutes on a cold run — Chrome starts, many scenarios exercise the live MIT OCW site). The command will likely exit with `BUILD FAILURE`. That is expected right now. What matters is that `run.log` now contains the real errors.

Open the Serenity report either way:

```
target/site/serenity/index.html
```

If `target/site/serenity/index.html` does **not** exist after the run, you probably ran `mvn verify` instead of `mvn -Pbdd verify`. The `bdd` profile is required — without it, no Cucumber scenarios run and no report is generated.

---

## 3. AI agent prompts — paste these in order

Each prompt is self-contained. Paste it verbatim into your AI agent (Cursor: Cmd+I or Cmd+L; Claude Code: just chat). Let the agent make the edits, then move to the next prompt. After all prompts are applied, go to Section 4.

### Prompt 3.1 — Fix the null-driver bug (the real blocker)

> In this repository, two Cucumber step classes capture the WebDriver at field initialization:
>
> - `src/test/java/com/cen4072/steps/SearchFilteringSteps.java`
> - `src/test/java/com/cen4072/steps/ResponsiveDesignSteps.java`
>
> Both have a line like `WebDriver driver = CommonSteps.getDriver();` near the top of the class. This runs when Cucumber instantiates the step class, which happens **before** the `@Before` hook in `CommonSteps` initializes the driver, so the field is permanently null and every scenario in those files fails with a NullPointerException.
>
> Fix both files by replacing the field with a method, using the exact same pattern already used in `src/test/java/com/cen4072/steps/ResourceTypesSteps.java`:
>
> ```java
> private static WebDriver driver() {
>     return java.util.Objects.requireNonNull(
>             CommonSteps.getDriver(),
>             "WebDriver not initialized (did @Before run?)");
> }
> ```
>
> Then update every usage of the `driver` field (not local parameters in lambdas) to call `driver()` instead. Do not change behavior or touch any other file. Show me a diff when done.

### Prompt 3.2 — Increase flaky waits

> In every Java file under `src/test/java/com/cen4072/steps/`, find every occurrence of `Duration.ofSeconds(5)` and change it to `Duration.ofSeconds(15)`. Leave `Duration.ofSeconds(90)` alone (that's a page-load timeout, not a wait). Do not change any file outside of `src/test/java/com/cen4072/steps/`.

### Prompt 3.3 — Dismiss the MIT OCW cookie banner once at startup

> Open `src/test/java/com/cen4072/steps/CommonSteps.java`. Add a private static helper method named `dismissConsentBannerIfPresent(WebDriver d)` that clicks a consent / "Accept" / "Got it" button if one is visible, and silently swallows any exception. Use a broad XPath that matches buttons whose text contains "accept", "got it", or "allow" (case-insensitive via `translate(...)`).
>
> Then, at the end of the `if (driver == null) { ... }` block inside the `setup()` method, call `driver.get(BASE_URL);` followed by `dismissConsentBannerIfPresent(driver);` so the banner is accepted exactly once for the shared browser session.

### Prompt 3.4 — Reset the browser viewport between scenarios

> In `src/test/java/com/cen4072/steps/CommonSteps.java`, add a new `@After(order = 9_999)` hook method named `resetViewportBetweenScenarios` that, if the driver is non-null, resizes the window back to `1440x900`. Wrap the resize in try/catch so a closed window during shutdown doesn't fail the suite. This prevents the mobile viewport from `ResponsiveDesignSteps` from leaking into later scenarios.

### Prompt 3.5 — Verify the fixes compile

> Now run `mvn -Pbdd test-compile` and report any compile errors. If there are compile errors, fix them — do not change test logic, only fix syntax issues you introduced.

---

## 4. Run it and verify

After the AI has applied all five prompts, you run:

```bash
mvn -Pbdd clean verify
```

Look for these signals of success:

1. Terminal ends with `BUILD SUCCESS`, **or** `BUILD FAILURE` with specific assertion / timeout messages (not compile errors and not "No tests were executed").
2. The file `target/site/serenity/index.html` exists.
3. Opening that file shows a non-zero scenario count. If it shows `0 tests`, you forgot `-Pbdd`.
4. At least one feature is fully green. For a demo, even 60–70% green across the four features is fine — MIT OCW is a live site and some flake is expected.

If scenarios still fail, paste the relevant section of `run.log` into your AI agent with this prompt:

> Here is the output from `mvn -Pbdd verify`. Tell me which scenario failed, why, and propose a minimal fix in the relevant step file under `src/test/java/com/cen4072/steps/`. Do not edit any file outside that folder unless I approve it first.

---

## 5. Voiceover script for the screen recording

The rubric (`rubric.txt`) requires a screen recording of the execution as a fallback. Read this script over the recording. Timing assumes roughly a 5–8 minute run.

> **[While the IDE is visible, before running]**
>
> "This is our CEN 4072 final project — an end-to-end automated test suite for MIT OpenCourseWare. We use Java 17, Selenium WebDriver 4, Cucumber with Gherkin for behavior-driven scenarios, and Serenity BDD for reporting. The build is managed by Maven.
>
> On the left you can see the project structure. Our Cucumber feature files live under `src/test/resources/features` — these are the plain-English scenarios. The matching Java step definitions are under `src/test/java/com/cen4072/steps`. The shared browser lifecycle is managed in `CommonSteps.java`, and the Cucumber entry point is `CucumberTestSuite.java`.
>
> We also have a parallel TestNG suite under `src/test/java/com/cen4072/suites` that satisfies the unit-test requirement in the rubric — eight test classes, each with five or more methods, wired together in `testng.xml`. The Cucumber suite on top of that gives us acceptance-level coverage."
>
> **[Start the run: `mvn -Pbdd clean verify`]**
>
> "I'm running `mvn -Pbdd clean verify`. The `-Pbdd` profile activates our Cucumber configuration — without it, only TestNG runs. Maven is now downloading dependencies, compiling the test classes, and handing control to the Failsafe plugin, which launches `CucumberTestSuite`. Serenity wraps each scenario, captures screenshots on failure, and aggregates everything into a living-documentation HTML report at the end."
>
> **[While scenarios run and Chrome automates]**
>
> "You can see a real Chrome browser being automated by Selenium. Each scenario opens MIT OCW, applies a filter or navigates to a course, and asserts on the DOM. We chose a shared-browser model — one Chrome instance across all scenarios — because MIT OCW is a live site, and spinning up a fresh browser per scenario triples the runtime without adding isolation value for read-only tests.
>
> Our feature files cover four areas: search filtering by topic, level, and department; resource-type filtering and PDF validation; responsive design across mobile, tablet, and desktop viewports; and user-preference interactions like the cookie banner and sidebar toggles."
>
> **[When the run finishes, open `target/site/serenity/index.html`]**
>
> "The run is complete. Serenity has generated the living documentation report. You can see each feature, each scenario, and the step-by-step execution trace. Failed scenarios include an automatic screenshot and the exact step that failed, which is extremely useful for debugging against a live third-party site.
>
> This satisfies our rubric requirements: a test suite larger than eight test classes, each with well-defined methods; a combined integration run via `testng.xml` for TestNG plus the Cucumber suite for acceptance testing; and real-world automation constraints — dynamic content, a cookie-consent banner, and variable network latency — which we handle with explicit waits, a startup banner-dismissal hook, and a viewport-reset hook between scenarios.
>
> That concludes our demonstration."

**Tip:** Record the screen with the Serenity report open at the end on the "Overall Test Results" dashboard. If the live run is too slow for the presentation slot, you can play the recording and narrate over it — which is exactly why the rubric requires one.

---

## 6. Finding the report / results after the fact

Anything you want to show your instructor lives here after a run:

- `target/site/serenity/index.html` — main report, open this in a browser.
- `target/site/serenity/home.html` — alternate landing page some versions generate.
- `target/failsafe-reports/` — raw XML output per scenario (useful if the HTML didn't render).

If `target/` is empty or missing after a run, you did not use `-Pbdd`. Repeat Section 4 with the correct command.

---

## 7. Quick AI-agent reference card

When in doubt, paste these into your agent verbatim:

- **"Run the Cucumber suite and tell me what failed":**
  > Run `mvn -Pbdd clean verify`, wait for it to finish, and summarize which scenarios passed and which failed. For each failure, quote the relevant stack trace from the output.

- **"I changed a step and it won't compile":**
  > Run `mvn -Pbdd test-compile` and fix any compilation errors you introduced. Do not change test logic, only fix syntax. Show me the diff.

- **"A selector broke because MIT OCW changed":**
  > The CSS selector `#search-page a[href*='/courses/']` appears in multiple step files. Open https://ocw.mit.edu/search/?q=python in a browser, inspect the current DOM, and tell me the correct replacement selector. Do not edit the files yet — just propose the change.

- **"Show me where a scenario is implemented":**
  > In `src/test/resources/features/`, find the scenario named "<name>". Then in `src/test/java/com/cen4072/steps/`, show me the Java methods that match each of its Given/When/Then lines.

---

## 8. Do not do these things

- Do not run `mvn test` and expect Cucumber. It runs TestNG only.
- Do not delete `@RunWith(CucumberWithSerenity.class)` from `CucumberTestSuite.java`. The `junit-vintage-engine` dependency is what makes that annotation work under Failsafe.
- Do not change `serenity.properties`. The shared-browser settings there are required by the custom driver management in `CommonSteps`.
- Do not commit anything under `target/` (already in `.gitignore`).
- Do not paste private data or credentials into your AI agent. This project talks to a public MIT site only — there is nothing secret to share.
