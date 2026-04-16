# Contributing to CEN 4072 Automation

Thank you for contributing to this software testing project! To maintain a professional standard, please follow these guidelines.

## 🥒 Writing Gherkin Scenarios
All new features should be documented in `.feature` files using clear, concise Gherkin.
- Use **Scenario Outlines** for data-driven tests.
- Keep "Given" steps focused on setup, "When" on actions, and "Then" on assertions.

## 💻 Java Code Standards
- **Page Object Model (POM):** Do not hardcode selectors in step definitions. Use Page Objects to encapsulate UI elements.
- **BaseTest Inheritance:** All test suites must extend `BaseTest` to ensure proper browser lifecycle management.
- **Wait Strategies:** Use `WebDriverWait` (Fluent Waits) instead of `Thread.sleep()` to ensure test stability.

## 🧪 Test Suite Structure
The project is organized into 8 core suites:
1. Navigation & Branding
2. Course Search
3. Search Filtering
4. Course Page Content
5. Resource Types
6. Responsive Design
7. User Preferences
8. Integration & Social

## 🚀 Pull Request Process
1. Ensure all tests pass locally using `mvn test`.
2. Generate a Serenity report to verify visual documentation.
3. Update the `README.md` if new dependencies are added.
