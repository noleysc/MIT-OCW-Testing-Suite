Feature: Search Filtering
  As a user searching for courses
  I want to filter results by topic, level, and department
  So that I can find exactly the educational materials I need

  Background:
    Given I am on the OCW search page

  Scenario: Filter by Topic
    When I apply the "Computer Science" topic filter
    Then I should see course results for "Computer Science"

  Scenario: Filter by Course Level
    When I apply the "Undergraduate" level filter
    Then I should see course results for "Undergraduate"

  Scenario: Filter by Department
    When I apply the "Physics" department filter
    Then I should see course results for "Physics"

  Scenario: Clear all filters
    Given I have applied "Engineering" topic and "Undergraduate" level filters
    When I click the "Clear All" button
    Then the active filters should be removed from the search

  Scenario: Multi-select filter combination
    When I apply "Engineering" topic and "Undergraduate" level filters
    Then I should see results matching both "Engineering" and "Undergraduate"
