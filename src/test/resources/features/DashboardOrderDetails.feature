Feature: Verify the Order Details screen from Dashboard

  @SmokeTest
  Scenario: Verify the total number of order count matches with database
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 1
    And click on login button
    Then verify the order count matches with database count for tenant from "TestData.xlsx" for Row number 1

  Scenario: Verify the options on DashBoard
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 1
    And click on login button
    Then Verify the options present on dashboard page from "TestData.xlsx" for Row number 1

  Scenario: Verify if the links are provided to all OrderId's
    Given User has the url for omsui and browser
    When user enters TenantID, username and Password from "TestData.xlsx" for Row number 1
    And click on login button
    Then user verifies the links are provided to all OrderId's


  Scenario: Submit the order and verify the order on UI
    Given User has the submit order details and UI details
    When User submits the order from "OrchestratorData" sheet for row number 1
    And log in to the UI by entering TenantID, username and Password from "TestData.xlsx" for Row number 1
    Then the order is shown in dashboard order details
