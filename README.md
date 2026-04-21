# MIT-OCW-Testing-Suite

## CEN 4072 Final Project: MIT OpenCourseWare Automation

This project is a comprehensive automated testing suite for the [MIT OpenCourseWare (OCW)](https://ocw.mit.edu/) platform, developed for the **CEN 4072 (Software Testing)** course. It validates functional and non-functional aspects of the site using both traditional TestNG suites and Behavior-Driven Development (BDD) with Cucumber and Serenity.

## Tech Stack
- **Language:** Java 17
- **Automation Engine:** Selenium WebDriver 4
- **Test Runner (Regression):** TestNG 7.8.0
- **Test Runner (BDD):** Cucumber with Serenity BDD 4.0.15
- **Build Tool:** Maven 3.9.6
- **Reporting:** TestNG HTML Reports & Serenity Living Documentation

## Project Structure
The project is organized into two main testing strategies:

1.  **TestNG Regression Suites:** Found in `src/test/java/com/cen4072/suites/`. These cover 8 core functional areas including Navigation, Search, and Filtering.
2.  **Cucumber BDD Scenarios:** Found in `src/test/resources/features/`. These use Gherkin syntax to define acceptance criteria, with step definitions in `src/test/java/com/cen4072/steps/`.

## How to Run Tests

### 1. Run TestNG Tests Only
Runs the 8 core regression suites defined in `testng.xml`.
```powershell
mvn clean test
```
- **Report Location:** `target/surefire-reports/index.html`

### 2. Run Cucumber Serenity Tests Only
Runs the BDD scenarios using the Maven `bdd` profile and Serenity's execution engine.
```powershell
mvn clean verify -Pbdd -DskipTests
```
- **Report Location:** `target/site/serenity/index.html`

### 3. Run the Entire Suite (Recommended)
Runs all TestNG tests followed by all Cucumber scenarios and generates an aggregated report.
```powershell
mvn clean verify -Pbdd
```
- **Report Locations:** 
  - TestNG: `target/surefire-reports/index.html`
  - Serenity: `target/site/serenity/index.html`

## Core Features Tested
- **Navigation & Branding:** Site identity, header/footer links, and core menu structures.
- **Course Search:** Accuracy of keyword search results and "No Results" handling.
- **Search Filtering:** Multi-level filtering by topic, sub-topic, level, and department.
- **Course Page Content:** Validation of elements on individual course homepages (Syllabus, Calendar, etc.).
- **Resource Types:** Validation of learning materials like PDFs, online textbooks, and lecture notes.
- **Responsive Design:** Verification across Mobile, Tablet, and Desktop viewports.
- **User Preferences:** Accessibility toggles, font resizing, and cookie consent handling.
- **Integration & Social:** External resource links and social media sharing functionality.

---
*Developed for FGCU CEN 4072 - Spring 2026*
