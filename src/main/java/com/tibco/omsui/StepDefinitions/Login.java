package com.tibco.omsui.StepDefinitions;

import com.tibco.omsui.config.ProjectPropertiesLoader;
import com.tibco.omsui.constant.ProjectConstant;
import com.tibco.omsui.pages.LoginPage;
import com.tibco.omsui.utils.Utility;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.Properties;


public class Login {
    String excelFilePath = "src/test/resources/";
    String tenantId, userName, password, browser;
    String OMSUI_URL;
    public static WebDriver driver;
    private static Utility utilities;
    LoginPage loginPage;
    @Given("User has the url for omsui and browser")
    public void user_has_the_url_for_omsui() {
        Properties property = ProjectPropertiesLoader.projectProperties();
        OMSUI_URL = property.getProperty("OMSUI_URL");
        browser = property.getProperty("browser");
        utilities = new Utility();
        driver = utilities.launchBrowser(browser,OMSUI_URL);
        loginPage = new LoginPage(driver);
    }

    @When("click on login button")
    public void click_on_login_button() {
        loginPage.clickLoginButton();
        loginPage.waitForLandingPageToLoad();
    }
    @Then("omsui landing page is displayed")
    public void omsui_landing_page_is_displayed() {
        loginPage.waitForLandingPageToLoad();
    }

    @Given("User is logged in to the omsui")
    public void user_is_logged_in_to_the_omsui() {
        System.out.println("User is logged in successfully");
    }

    String pageTitle;
    @Then("Title is shown as expected")
    public void title_is_shown_as() {
        String expectedTitle = ProjectConstant.DASHBOARD_TITLE;
        loginPage.waitForPageTitle(expectedTitle);
        pageTitle = utilities.validateTitleOfThePage();
        if (expectedTitle.equals(pageTitle)){
            Assertions.assertTrue(true,"Page title is matched successfully");
        }
        else{
            Assertions.assertEquals(expectedTitle, pageTitle, "Page title is not getting matched");
        }
    }

    @When("user enters TenantID, username and Password from {string} for Row number {int}")
    public void userEntersDetailsForInvalidUserName(String fileName, int rowNum) {
        fileName = excelFilePath.concat(fileName);
        Utility.loadExcelFile(fileName);
        tenantId = Utility.getCellData("Login",rowNum,0);
        userName = Utility.getCellData("Login",rowNum,1);
        password = Utility.getCellData("Login",rowNum,2);
        loginPage.userLogin(tenantId,userName,password);
    }

    @But("error is displayed for invalid user from {string} for Row number {int}")
    public void errorIsDisplayedForUser(String fileName, int rowNum) {
        fileName = excelFilePath.concat(fileName);
        Utility.loadExcelFile(fileName);
        String expectedMsg = Utility.getCellData("Login",rowNum,3);
        loginPage.validateInvalidUserLoginMessage(expectedMsg);
    }

    @But("error is displayed for invalid tenant from {string} for Row number {int}")
    public void errorIsDisplayedForTenant(String fileName, int rowNum) {
        fileName = excelFilePath.concat(fileName);
        Utility.loadExcelFile(fileName);
        String expectedMsg = Utility.getCellData("Login",rowNum,3);
        loginPage.validateInvalidTenantLoginMessage(expectedMsg);
    }

    @But("error is displayed for invalid password from {string} for Row number {int}")
    public void errorIsDisplayedForPassword(String fileName, int rowNum) {
        fileName = excelFilePath.concat(fileName);
        Utility.loadExcelFile(fileName);
        String expectedMsg = Utility.getCellData("Login",rowNum,3);
        loginPage.validateInvalidPasswordLoginMessage(expectedMsg);
    }


    @When("User verifies the microsoft login")
    public void userVerifiesTheMicrosoftLogin() {
        System.out.println("Verifying the microsoft login availability");
    }

    @Then("Microsoft Login button is displayed")
    public void microsoftLoginButtonIsDisplayed() {
        loginPage.verifyMicrosoftLogin();
    }

    @When("user log in to the UI from {string} for Row number {int}")
    public void userLogInToTheUIFromForRowNumber(String fileName, int rowNum) {
        userEntersDetailsForInvalidUserName(fileName,rowNum);
        click_on_login_button();
        omsui_landing_page_is_displayed();
    }

    @And("mouse hover on the profile icon")
    public void mouseHoverOnTheProfileIcon() {
        loginPage.mouseHoverOnProfileIcon();
    }

    @Then("username, TenantId and logout button is displayed")
    public void usernameTenantIdAndLogoutButtonIsDisplayed() {
        loginPage.validateDetailsOnProfileIcon(tenantId,userName);
    }
}
