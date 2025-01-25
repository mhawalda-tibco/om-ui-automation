package com.tibco.omsui.pages;

import com.tibco.omsui.utils.Utility;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

public class DashboardPage {

    Utility utilities = new Utility();
    public WebDriver driver;

    @FindBy(id="search-section-table")
    WebElement findOrderTable;

    @FindBy(xpath="//table[@id='search-section-table']/descendant::tbody/tr")
    WebElement findOrderRows;

    @FindBy(id="mui-23")
    WebElement ordersPerPage;

    @FindBy(xpath="//*[@id=\"mui-23\"]/following::p[contains(@class,\"MuiTablePagination-displayedRows\")]")
    WebElement paginationCount;

    @FindBy(xpath="//button[@title=\"Go to next page\"]")
    WebElement nextPage;

    @FindBy(xpath="//nav[@aria-label=\"navigation-box\"]/descendant::ul[contains(@class,'MuiList-root MuiList-padding')]/div")
    List<WebElement> dashboardOptions;

    @FindBy(xpath="//nav[@aria-label=\"navigation-box\"]/descendant::ul[contains(@class,'MuiList-root MuiList-padding')]/descendant::span[contains(text(),\"Dashboard\")]")
    WebElement dashboardLink;

    @FindBy(xpath="//nav[@aria-label=\"navigation-box\"]/descendant::ul[contains(@class,'MuiList-root MuiList-padding')]/descendant::span[contains(text(),\"Bulk Action Job\")]")
    WebElement bulkActionJobLink;

    @FindBy(xpath="//nav[@aria-label=\"navigation-box\"]/descendant::ul[contains(@class,'MuiList-root MuiList-padding')]/descendant::span[contains(text(),\"Saved Searches\")]")
    WebElement savedSearchesLink;

    @FindBy(xpath="//nav[@aria-label=\"navigation-box\"]/descendant::ul[contains(@class,'MuiList-root MuiList-padding')]/descendant::span[contains(text(),\"Jeopardy Rules\")]")
    WebElement jeopardyRulesLink;

    public DashboardPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public int getColumnNumberForHeader(String headerName){
        List<WebElement> thead;
        int orderIdColumn = 0;
        utilities.waitForElementVisibility(findOrderTable);
        thead = findOrderTable.findElements(By.xpath(".//thead/tr/th"));
        for(int i=1;i<=thead.size();i++){
            if(thead.get(i).getText().equals(headerName)){
                orderIdColumn = i;
                break;
            }
        }
        return orderIdColumn;
    }

    public boolean findOrderIdInOrderDetailsTable(String orderId){
        int orderIdColumn = getColumnNumberForHeader("Order ID") + 1;
        boolean flag = false;
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='search-section-table']/descendant::tbody/tr"));
        for (WebElement row : rows) {
            String actualOrderId = row.findElement(By.xpath("./td["+orderIdColumn+"]")).getText();
            if (actualOrderId.equals(orderId)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void checkTheHyperLinkForOrderId() {
        int orderIdColumn = getColumnNumberForHeader("Order ID") + 1;
        int orderPerPageCount = getOrderPerPageCount();
        int totalNoOfOrders = getTotalNumberOfOrders();
        int totalNumberOfPages = (int) Math.ceil((double) totalNoOfOrders / orderPerPageCount);

        int higherCount = 0;

        for (int i = 1; i <= totalNumberOfPages; i++) {
            List<WebElement> rows = getTableRows();
            int lowerCount = 1 + higherCount;
            higherCount = 10 + higherCount;

            verifyPageCount(lowerCount, higherCount);

            processRows(rows, orderIdColumn);

            if (i < totalNumberOfPages) {
                navigateToNextPage(i);
            }
        }
    }

    public int getOrderPerPageCount() {
        utilities.waitForElementVisibility(ordersPerPage);
        String orderIdCountString = ordersPerPage.getText().trim();
        return Integer.parseInt(orderIdCountString);
    }

    public int getTotalNumberOfOrders() {
        utilities.waitForElementVisibility(paginationCount);
        String totalOrdersString = paginationCount.getText().trim();
        String[] totalOrderCountString = totalOrdersString.split("of");
        return Integer.parseInt(totalOrderCountString[1].trim());
    }

    public List<WebElement> getTableRows() {
        return driver.findElements(By.xpath("//table[@id='search-section-table']/descendant::tbody/tr"));
    }

    public void verifyPageCount(int lowerCount, int higherCount) {
        String pageNumbers = paginationCount.getText().trim().split("of")[0].trim();
        String expectedPageNo = lowerCount + "-" + higherCount;
        String actualPageNo = pageNumbers.replace("–", "-");

        if (!actualPageNo.equals(expectedPageNo)) {
            throw new RuntimeException("Page count does not match. Expected: " + expectedPageNo + ", Actual: " + actualPageNo);
        }
    }

    public void processRows(List<WebElement> rows, int orderIdColumn) {
        for (WebElement row : rows) {
            utilities.waitForElementVisibility(row);
            String orderId = row.findElement(By.xpath(".//td[" + orderIdColumn + "]")).getText().trim();

            boolean isElementPresent = isHyperLinkPresent(row, orderIdColumn);
            if (isElementPresent) {
                System.out.println("Link is present for Order ID: " + orderId);
            } else {
                throw new RuntimeException("Link is not present for Order ID: " + orderId);
            }
        }
    }

    public boolean isHyperLinkPresent(WebElement row, int orderIdColumn) {
        return !row.findElements(By.xpath(".//td[" + orderIdColumn + "]/a")).isEmpty();
    }

    public void navigateToNextPage(int currentPage) {
        try {
            nextPage.click();
            Thread.sleep(2000);
            System.out.println("Navigated to page: " + (currentPage + 1));
        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate to the next page at page: " + currentPage, e);
        }
    }

    public void verifyDashboardOptionsSize(List<String> rowData) {
        if(rowData.size() == dashboardOptions.size()){
            System.out.println("Dashboard options size matches with expected size: " + dashboardOptions.size());
        }
        else
            throw new RuntimeException("Dashboard option size mismatched. Expected size is "
            + rowData.size()+" and actual size is "+dashboardOptions.size());
    }

    public void verifyDashboardOptionsValues(List<String> expectedValues) {
        List<String> actualValues = Arrays.asList(
                dashboardLink.getText(),
                bulkActionJobLink.getText(),
                savedSearchesLink.getText(),
                jeopardyRulesLink.getText()
        );

        verifyDashboardOptionsSize(expectedValues);

        for (int i = 0; i < expectedValues.size(); i++) {
            if (!expectedValues.get(i).equals(actualValues.get(i))) {
                throw new RuntimeException(
                        String.format("Mismatch at index %d: expected '%s' but found '%s'.",
                                i, expectedValues.get(i), actualValues.get(i)));
            }
        }
    }
}
