package com.tibco.omsui.Action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.omsui.Model.SubmitOrderRequest;
import com.tibco.omsui.config.ProjectPropertiesLoader;
import com.tibco.omsui.constant.ProjectConstant;
import com.tibco.omsui.utils.ExcelUtility;
import com.tibco.omsui.utils.RequestSpecUtility;
import io.restassured.response.Response;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class SubmitOrder {

    public static SubmitOrderRequest submitOrderRequest;

    public SubmitOrderRequest createOrderRequest(List<String> rowData) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String requestFile = ProjectConstant.REQUEST_FILE_PATH.concat(rowData.get(3));
            File jsonRequest = new File(requestFile);
            submitOrderRequest = objectMapper.readValue(jsonRequest, SubmitOrderRequest.class);
            String randomOrderRef = "UITest_" + UUID.randomUUID().toString();
            submitOrderRequest.getOrderRequest().setOrderRef(randomOrderRef);
            System.out.println("OrderId is: " + submitOrderRequest.getOrderRequest().getOrderRef());
            //objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("C:\\Users\\MudassarDastagir.Haw\\UIAutomation\\src\\main\\java\\com\\tibco\\omsui\\Requests\\updatedSimpleSubmitOrder.json"), submitOrderRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return submitOrderRequest;
    }

    public Response submitOrder(String sheetName,int rowNum) throws IOException, InvalidFormatException {
        RequestSpecUtility utilities = new RequestSpecUtility();
        Properties property = ProjectPropertiesLoader.projectProperties();
        String baseUri = property.getProperty("ORCH_URL");
        ExcelUtility excelUtility = new ExcelUtility(ProjectConstant.TESTDATA_FILE);
        List<String> rowData = excelUtility.getRowData(sheetName,rowNum);
        Authorization authorization = new Authorization();
        String token = authorization.getAuthToken();
        String accessToken = "Bearer "+ token;
        submitOrderRequest = createOrderRequest(rowData);
        Response response = utilities.executeApiRequest(baseUri, rowData.get(1), rowData.get(2),accessToken,submitOrderRequest,Integer.parseInt(rowData.get(4)));
        System.out.println("Order submission response: " + response.getStatusCode());
        return response;
    }
}