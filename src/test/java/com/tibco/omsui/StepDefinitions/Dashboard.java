package com.tibco.omsui.StepDefinitions;

import com.tibco.omsui.DataBase.DataBaseMethods;
import com.tibco.omsui.config.ProjectPropertiesLoader;
import com.tibco.omsui.constant.ProjectConstant;
import com.tibco.omsui.pages.DashboardPage;
import com.tibco.omsui.utils.ExcelUtility;
import com.tibco.omsui.utils.Utility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import com.tibco.omsui.Action.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Dashboard {
    String excelFilePath = "src/test/resources/";
    Login login = new Login();
    SubmitOrder submitOrder = new SubmitOrder();
    DashboardPage dashboardPage;

    @Given("User has the submit order details and UI details")
    public void userHasTheSubmitOrderDetailsAndUIDetails() throws IOException, InvalidFormatException {
        ExcelUtility excelUtility = new ExcelUtility(ProjectConstant.TESTDATA_FILE);
        List<String> rowData = excelUtility.getRowData("OrchestratorData",1);
        SubmitOrder.submitOrderRequest = submitOrder.createOrderRequest(rowData);
        login.user_has_the_url_for_omsui();
    }
    String orderId = null;
    @When("User submits the order from {string} sheet for row number {int}")
    public void userSubmitsTheOrderFromSheetForRowNumber(String sheetName, int rowNum) throws IOException, InvalidFormatException {
        Response response = submitOrder.submitOrder(sheetName,rowNum);
        orderId = SubmitOrder.submitOrderRequest.getOrderRequest().getOrderRef();
    }

    @And("log in to the UI by entering TenantID, username and Password from {string} for Row number {int}")
    public void loginToTheUI(String fileName, int rowNum) {
        login.userEntersDetailsForInvalidUserName(fileName,rowNum);
        login.click_on_login_button();
    }

    @Then("the order is shown in dashboard order details")
    public void theOrderIsShownInDashboardOrderDetails() {
        dashboardPage= new DashboardPage(Login.driver);
        boolean flag = dashboardPage.findOrderIdInOrderDetailsTable(orderId);
        if (flag)
            Assert.assertTrue(true);
        else
            throw new RuntimeException("OrderId "+orderId+" is not found in Find Order table");
    }

    @Then("user verifies the links are provided to all OrderId's")
    public void userVerifiesTheLinksAreProvidedToAllOrderIdS() {
        dashboardPage= new DashboardPage(Login.driver);
        dashboardPage.checkTheHyperLinkForOrderId();
    }

    @Then("verify the order count matches with database count for tenant from {string} for Row number {int}")
    public void verifyTheOrderCountMatchesWithDatabaseCount(String fileName, int rowNum) throws SQLException, ClassNotFoundException {
        fileName = excelFilePath.concat(fileName);
        Utility.loadExcelFile(fileName);
        String tenantId = Utility.getCellData("Login",rowNum,0);
        Properties property = ProjectPropertiesLoader.projectProperties();
        String driverClassName = property.getProperty("datasourceDriverClassName");
        String dbUrl = property.getProperty("archivalDsUrl");
        String dbUsername = property.getProperty("archivalDsUsername");
        String dbPassword = property.getProperty("archivalDsPassword");
        dashboardPage= new DashboardPage(Login.driver);

        DataBaseMethods dbMethods = new DataBaseMethods();

        String query = "SELECT COUNT(*) AS count FROM orders_abstract WHERE tenant_id = '"+tenantId+"'";

        int expectedCount = dbMethods.executeQueryForCount(query, dbUrl, dbUsername, dbPassword, driverClassName);

        int actualCount = dashboardPage.getTotalNumberOfOrders();

        if (actualCount != expectedCount) {
            throw new RuntimeException("Orders count mismatch: UI count = " + actualCount + ", DB count = " + expectedCount);
        } else {
            System.out.println("Orders count matched successfully: UI count = " + actualCount + ", DB count = " + expectedCount);
        }
    }

    @Then("Verify the options present on dashboard page from {string} for Row number {int}")
    public void verifyTheOptionsPresentOnDashboardPageFromForRowNumber(String fileName, int rowNum) throws IOException, InvalidFormatException {
        ExcelUtility excelUtility = new ExcelUtility(ProjectConstant.TESTDATA_FILE);
        List<String> rowData = excelUtility.getRowData("Dashboard",1);
        dashboardPage= new DashboardPage(Login.driver);
        dashboardPage.verifyDashboardOptionsValues(rowData);
    }
}
