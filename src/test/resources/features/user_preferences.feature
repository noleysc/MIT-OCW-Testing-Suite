Feature: User Preferences
  As a regular user of OCW
  I want to customize my viewing experience
  So that I can learn comfortably

  Scenario: Sidebar toggle (Expand/Collapse)
    Given I am on a sample course page
    When I toggle the course sidebar
    Then the sidebar should expand or collapse correctly

  Scenario: Cookie consent banner
    Given I am on the home page
    When I accept the cookie consent
    Then the consent banner should disappear

  Scenario: Dark mode toggle
    Given I am on a sample course page
    When I toggle the dark mode
    Then the page should transition between light and dark themes

  Scenario: Sidebar expansion state
    Given I am on a sample course page
    When I toggle the course sidebar "twice"
    Then the sidebar should return to its initial state

  Scenario: Cookie consent persistence
    Given I have accepted the cookie consent
    When I refresh the page
    Then the consent banner should not be visible
