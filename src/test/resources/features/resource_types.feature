Feature: Resource Types
  As a learner looking for specific educational materials
  I want to filter courses by resource type and access direct materials
  So that I can study with my preferred media

  Scenario: Filter for Online Textbooks
    Given I search for "Open Textbooks" resource type
    Then I should see course results listed for textbooks

  Scenario: Filter for Interactive Simulations
    Given I search for "Simulations" resource type
    Then I should see course results listed for simulations

  Scenario: Resource download link validation
    Given I am on the download page for a sample course
    Then I should find a valid PDF resource link

  Scenario: Assignments page access
    Given I navigate to the assignments page of a sample course
    Then I should see course assignment content

  Scenario: Learning resource types on course home
    Given I am on the main page of a sample course
    Then I should see "Lecture Videos" listed as a resource
