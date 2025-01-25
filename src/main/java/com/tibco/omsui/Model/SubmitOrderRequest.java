package com.tibco.omsui.Model;

public class SubmitOrderRequest {

    String externalBusinessTransactionId;
    OrderRequest orderRequest;
    public String getExternalBusinessTransactionId() {
        return externalBusinessTransactionId;
    }

    public void setExternalBusinessTransactionId(String externalBusinessTransactionId) {
        this.externalBusinessTransactionId = externalBusinessTransactionId;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

}
