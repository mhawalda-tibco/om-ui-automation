package com.tibco;

import com.tibco.omsui.Action.Authorization;
import com.tibco.omsui.Action.SubmitOrder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        Authorization auth = new Authorization();
        auth.fetchAuthToken();
        SubmitOrder submitOrder = new SubmitOrder();
        //submitOrder.submitOrder();
    }
}