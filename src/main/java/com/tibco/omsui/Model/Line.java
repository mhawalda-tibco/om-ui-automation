package com.tibco.omsui.Model;

import java.util.List;

public class Line {

    private String action;
    private Address deliveryAddress;
    private Address invoiceAddress;
    private String lineNumber;
    private String productID;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSubscriberID() {
        return subscriberID;
    }

    public void setSubscriberID(String subscriberID) {
        this.subscriberID = subscriberID;
    }

    public List<Udf> getUdf() {
        return udf;
    }

    public void setUdf(List<Udf> udf) {
        this.udf = udf;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    private String productVersion;
    private int quantity;
    private String subscriberID;
    private List<Udf> udf;
    private String uom;

}
