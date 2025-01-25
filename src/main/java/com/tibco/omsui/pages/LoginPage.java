package com.tibco.omsui.pages;

import com.tibco.omsui.utils.Utility;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    Utility utilities = new Utility();
    public WebDriver driver;

    @FindBy(id="tenantId")
    WebElement tenantIdEl;

    @FindBy(id="username")
    WebElement userNameEl;

    @FindBy(id="password")
    WebElement passwordEl;

    @FindBy(xpath="//button[@type='submit']")
    WebElement loginBtnEl;

    @FindBy(xpath = "//*[contains(text(),'Invalid User')]")
    WebElement invalidUserMsg;

    @FindBy(xpath = "//*[contains(text(),'Invalid Tenant')]")
    WebElement invalidTenantMsg;

    @FindBy(xpath = "//*[contains(text(),'Invalid Password')]")
    WebElement invalidPasswordMsg;

    @FindBy(xpath = "//section[@class='home-charts-section']")
    WebElement homeChartSection;

    @FindBy(xpath = "//button[contains(text(),'Login with Microsoft')]")
    WebElement microsoftLogin;

    @FindBy(xpath = "//button[@data-testid='profile-button']")
    WebElement profileIcon;

    @FindBy(xpath="//li[@aria-label='username']")
    WebElement profileUserName;

    @FindBy(xpath="//li[@aria-label='tenant']")
    WebElement profileTenant;

    @FindBy(xpath="//li[@aria-label='tenant']/following-sibling::li")
    WebElement profileLogout;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void userLogin(String tenantId, String username, String password){
        tenantIdEl.sendKeys(tenantId);
        userNameEl.sendKeys(username);
        passwordEl.sendKeys(password);
    }

    public void clickLoginButton(){
        loginBtnEl.click();
    }

    public void validateInvalidUserLoginMessage(String message){
        utilities.waitForElementVisibility(invalidUserMsg);
        String actualMessage = invalidUserMsg.getText();
        Assert.assertEquals(message,actualMessage);
    }

    public void validateInvalidTenantLoginMessage(String message){
        utilities.waitForElementVisibility(invalidTenantMsg);
        String actualMessage = invalidTenantMsg.getText();
        Assert.assertEquals(message,actualMessage);
    }

    public void validateInvalidPasswordLoginMessage(String message){
        utilities.waitForElementVisibility(invalidPasswordMsg);
        String actualMessage = invalidPasswordMsg.getText();
        Assert.assertEquals(message,actualMessage);
    }

    public void waitForLandingPageToLoad(){
        utilities.waitForElementVisibility(homeChartSection);
    }

    public void waitForPageTitle(String expectedTitle){
        utilities.waitForPageTitle(expectedTitle);
    }

    public void verifyMicrosoftLogin() {
        Assert.assertTrue(microsoftLogin.isDisplayed());
    }

    public void mouseHoverOnProfileIcon() {
        utilities.waitForElementVisibility(profileIcon);
        Actions actions = new Actions(driver);
        actions.moveToElement(profileIcon).build().perform();
    }

    public void validateDetailsOnProfileIcon(String tenantId, String userName) {
        utilities.waitForElementVisibility(profileTenant);
        String actualTenantId = profileTenant.getText();
        String[] splitString = actualTenantId.split("\n");
        actualTenantId = splitString[1];
        Assert.assertEquals(tenantId,actualTenantId);
        String actualUserName = profileUserName.getText();
        String[] splitStringUsername = actualUserName.split("\n");
        actualUserName = splitStringUsername[1];
        Assert.assertEquals(userName,actualUserName);
        Assert.assertTrue(profileLogout.isDisplayed());
        String logoutText = profileLogout.getText();
        Assert.assertEquals("Logout",logoutText);
    }
}
