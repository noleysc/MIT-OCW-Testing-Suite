# CEN 4072: Fundamentals of Software Testing – Spring 2026
## Final Project Report: Automated Regression Testing of the MIT OpenCourseWare (OCW) Platform

---

### 1. Project Description

#### 1.1 Project Title Page
**Project Title:** Automated Regression Testing of the MIT OpenCourseWare (OCW) Platform  
**Instructor Name:** [Instructor Name]  
**Course Name & Code:** CEN 4072: Fundamentals of Software Testing  
**Group Number:** [Group Number]  
**Team Members:**  
*   [Your Name] (Student ID: [Your ID])  
*   Chris [Surname] (Student ID: [Chris's ID])  

#### 1.2 Project Goal
The primary objective of this project is to develop and implement a robust automated regression testing suite for the **MIT OpenCourseWare (OCW)** platform. As the platform serves as a critical global repository for educational content, maintaining its functional integrity is essential. This testing suite aims to validate core user journeys, ensure the accessibility of educational resources, and verify that system updates do not introduce regressions in existing functionalities.

#### 1.3 System & Test Summary
The system under test is the **MIT OpenCourseWare (OCW)** web application (https://ocw.mit.edu/). Our testing approach focuses on **Regression Testing**, covering the following four primary functional domains:

1.  **Navigation & Branding:** Validates the presence and functionality of the global header, footer, and branding elements. This includes ensuring the logo correctly redirects to the home page and that essential call-to-action buttons (like "Give Now") are visible.
2.  **Course Search:** Verifies the core search functionality, including keyword matching (e.g., "Python"), handling of special characters, and "no results" UI feedback.
3.  **Course Page Content:** Ensures that critical course-level data—such as the syllabus, instructor information, and course number—is correctly rendered and accessible to the user.
4.  **Integration & Social:** Validates the integrity of external links, including social sharing (Twitter/X), MIT Open Learning redirects, and the Zendesk-powered Help/FAQ navigation which requires multi-window handle management.

The framework is developed using **Java 17**, **Selenium WebDriver 4**, and **TestNG**.

#### 1.4 Workflow Diagram
*(Note: A high-resolution diagram is included in the project's presentation materials.)*

**Automated Testing Pipeline:**
1.  **Identification:** Pinpointing critical user flows and dynamic elements on the OCW platform.
2.  **Design:** Authoring targeted test cases to address functional requirements and automation constraints.
3.  **Implementation:** Developing TestNG suites leveraging a Page Object Model (POM) inspired architecture and fluent wait strategies.
4.  **Execution:** Running the integrated suite via `testng.xml` to verify system-wide stability.
5.  **Validation:** Analyzing execution results and logs to confirm architectural integrity.

#### 1.5 Code Repository & Submission
**GitHub Link:** [Link to your Public Repository]  
**Code Submission:** Code archive is attached to this report submission.  
*(State: “Code archive is attached to this report submission.”)*

---

### 2. Work Conducted

#### 2.1 Overview of Work
The project involved the end-to-end development of an automation framework. A significant technical challenge addressed was the management of **dynamic elements** and **multi-window navigation** (e.g., Zendesk support redirects). We implemented explicit wait strategies (`WebDriverWait`) to handle asynchronous content loading, moving away from brittle `Thread.sleep` calls.

The test development focused on:
*   **Selector Optimization:** Using robust CSS and XPath selectors to navigate the OCW DOM.
*   **Assertion Logic:** Implementing comprehensive TestNG assertions to verify UI state and URL redirects.
*   **Window Management:** Managing browser window handles for external integration tests.

#### 2.2 Individual Contributions
*   **[Your Name]:** Responsible for the architectural foundation, including the `BaseTest` lifecycle management, and the development of the *Navigation & Branding* and *Course Search* test modules. [Your Name] also implemented the logic for handling dynamic search result validation.
*   **Chris:** Chris was responsible for [BLANK - CHRIS TO FILL IN], specifically focusing on the [BLANK - CHRIS TO FILL IN]. His contributions included [BLANK - CHRIS TO FILL IN].

---

### 3. Tools Used
*   **Java 17:** Core programming language.
*   **Selenium WebDriver 4:** Browser automation engine.
*   **TestNG:** Test orchestration and assertion framework.
*   **Maven:** Build and dependency management.
*   **Git / GitHub:** Version control and collaboration.

---

### 4. Motivation
The motivation behind this testing project stems from the global impact of MIT OCW. As near-graduate software engineers, we recognize that automated testing is the only way to ensure quality at scale. 
*   **Preventing Interruptions:** By automating checks for syllabus and lecture video accessibility, we prevent disruptions for millions of global learners.
*   **Handling Constraints:** Our implementation explicitly addresses automation constraints like pop-ups and external redirects, which are common in real-world web applications but often overlooked in basic testing.
*   **Impact:** A robust test suite improves the system's long-term reliability and allows for faster deployment of new educational features.

---

### 5. Appendix
The attached ZIP file contains:
*   All source/test code (.java files).
*   The `pom.xml` configuration file.
*   The `testng.xml` integration suite.
*   Screenshots of passed execution cycles.
