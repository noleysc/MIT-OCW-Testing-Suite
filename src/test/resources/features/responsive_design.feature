Feature: Responsive Design
  As a mobile or tablet user
  I want the OCW site to be readable and functional on my device
  So that I can learn on the go

  Scenario: Mobile phone view (iPhone SE)
    When I resize the browser to "375x667"
    Then the mobile navigation menu should be visible

  Scenario: Tablet view (iPad Air)
    When I resize the browser to "820x1180"
    Then the site should adapt to tablet layout

  Scenario: Desktop view
    When I resize the browser to "1920x1080"
    Then the full desktop header should be visible

  Scenario: Course search on mobile
    Given I have resized the browser to "375x667"
    When I search for "Python"
    Then the search results should be readable on mobile

  Scenario: Grid layout adjustment
    When I resize the browser to "375x667"
    Then the course cards should stack in a single column
