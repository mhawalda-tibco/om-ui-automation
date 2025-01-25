Feature: Verify the login page

  Scenario: Login to the UI and verify landing page
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 1
    And click on login button
    Then omsui landing page is displayed
    And Title is shown as expected

  Scenario: Login to the UI with Invalid username and verify error message
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 2
    And click on login button to check the error
    But error is displayed for invalid user from "TestData.xlsx" for Row number 2

  Scenario: Login to the UI with Invalid tenant and verify error message
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 3
    And click on login button to check the error
    But error is displayed for invalid tenant from "TestData.xlsx" for Row number 3

  Scenario: Login to the UI with Invalid password and verify error message
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 4
    And click on login button to check the error
    But error is displayed for invalid password from "TestData.xlsx" for Row number 4

  Scenario: Verify if Microsoft Login is present in omsui
    Given User has the url for omsui and browser
    When User verifies the microsoft login
    Then Microsoft Login button is displayed

  Scenario: Verify login icon on homepage
    Given User has the url for omsui and browser
    When user log in to the UI from "TestData.xlsx" for Row number 1
    And mouse hover on the profile icon
    Then username, TenantId and logout button is displayed
