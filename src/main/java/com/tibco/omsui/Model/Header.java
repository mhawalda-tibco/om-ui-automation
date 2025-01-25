package com.tibco.omsui.Model;

import java.util.List;

public class Header {

    private Address deliveryAddress;
    private String description;
    private int orderPriority;

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(int orderPriority) {
        this.orderPriority = orderPriority;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<Udf> getUdf() {
        return udf;
    }

    public void setUdf(List<Udf> udf) {
        this.udf = udf;
    }

    public String getRequiredByDate() {
        return requiredByDate;
    }

    public void setRequiredByDate(String requiredByDate) {
        this.requiredByDate = requiredByDate;
    }

    private String customerID;
    private List<Udf> udf;
    private String requiredByDate;

}
