# MIT-OCW-Testing-Suite
=======
# CEN 4072 Final Project: MIT OpenCourseWare Automation

This project is a comprehensive automated testing suite for the [MIT OpenCourseWare (OCW)](https://ocw.mit.edu/) platform, developed as the final project for **CEN 4072 (Software Testing)**.

## Tech Stack
- **Language:** Java 17
- **BDD Framework:** Cucumber (using **Gherkin** syntax)
- **Automation Engine:** Selenium WebDriver 4
- **Reporting & Orchestration:** Serenity BDD
- **Build Tool:** Maven 3.9.6
- **Test Runner:** JUnit 5

## BDD & Gherkin Strategy
This project follows a **Behavior-Driven Development (BDD)** approach. We use **Gherkin** to bridge the gap between technical implementation and business requirements:
- **Given:** Initial context (e.g., "Given I am on the home page")
- **When:** User action (e.g., "When I search for 'Python'")
- **Then:** Expected outcome (e.g., "Then I should see search results")

## Serenity BDD Reporting
Serenity BDD is used to generate "Living Documentation." After running the tests, it produces a rich HTML report that includes:
- Step-by-step execution details.
- Automatic screenshots of failures.
- Functional coverage requirements.
- Narrative reports for stakeholders.

## How to Run
To execute the full suite and generate the Serenity report:
```powershell
mvn clean verify
```
The report will be available at: `target/site/serenity/index.html`
>>>>>>> e6c28a6 (Initial commit for CEN 4072 Final Project - MIT OCW Automation Framework)
