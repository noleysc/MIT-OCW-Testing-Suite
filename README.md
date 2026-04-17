# MIT-OCW-Testing-Suite
=======
# CEN 4072 Final Project: MIT OpenCourseWare Automation

This project is a comprehensive automated testing suite for the [MIT OpenCourseWare (OCW)](https://ocw.mit.edu/) platform, developed as the final project for **CEN 4072 (Software Testing)**.

## Tech Stack
- **Language:** Java 17
- **Test Runner:** TestNG 7.8.0
- **Automation Engine:** Selenium WebDriver 4
- **Reporting:** Allure Report
- **Build Tool:** Maven 3.9.6

## Project Structure
- `src/test/java/com/cen4072/base/`: Contains `BaseTest.java` for centralized WebDriver setup/teardown using TestNG `@BeforeSuite` and `@AfterSuite`.
- `src/test/java/com/cen4072/suites/`: Contains functional test suites migrated to TestNG with Allure annotations.
- `testng.xml`: Configuration file for defining test suites and execution parameters.
- `pom.xml`: Project Object Model file with updated dependencies for TestNG, Allure, and AspectJ.

## Running Tests
To execute the full suite:
```powershell
mvn clean test
```
This will run the tests as defined in `testng.xml` and generate results in the `allure-results` folder.

## Allure Reporting
Allure Report is used for rich, interactive test documentation. After running the tests, you can generate and view the report:

1. **Generate the report:**
   ```powershell
   mvn allure:report
   ```
2. **Open the report:**
   ```powershell
   mvn allure:serve
   ```
The report provides step-by-step execution details, trends, and detailed failure analysis.

## Development Conventions
1. **Inheritance:** All test suites must extend `BaseTest`.
2. **Annotations:** Use `@Test(description = "...")` for test cases and Allure's `@Epic` and `@Feature` for classification.
3. **Assertions:** Use `org.testng.Assert` for all validations.
4. **Wait Strategies:** Use `WebDriverWait` (Explicit/Fluent Waits) for robust element interaction.
