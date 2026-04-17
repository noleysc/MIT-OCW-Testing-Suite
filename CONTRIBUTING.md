# Contributing to MIT-OCW-Testing-Suite

Thank you for contributing! Please follow these guidelines to ensure the quality and consistency of the automation suite.

## Development Workflow

1.  **Branching Strategy:** Create a new branch for each feature or bug fix: `git checkout -b feature/your-feature-name`.
2.  **Test Implementation:**
    *   Extend `BaseTest`.
    *   Use TestNG `@Test(description = "...")` for all test methods.
    *   Include Allure `@Epic` and `@Feature` annotations at the class level.
    *   Use `@Step` for reusable helper methods.
3.  **Assertions:** Use `org.testng.Assert` for all validations.
4.  **Verification:** Before submitting a PR, ensure all tests pass and generate an Allure report to verify the results.

## Reporting & Documentation

We use **Allure Report** for living documentation. Every test should be clearly described so that non-technical stakeholders can understand the coverage.

### Commands
*   Run tests: `mvn clean test`
*   Generate report: `mvn allure:report`
*   Serve report: `mvn allure:serve`

## Code Style
*   Follow standard Java naming conventions (PascalCase for classes, camelCase for methods/variables).
*   Use meaningful names for test methods (e.g., `testSearchByValidKeyword`).
*   Keep test methods focused and independent.
