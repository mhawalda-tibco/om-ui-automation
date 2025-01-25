package com.tibco.omsui.Model;

import java.util.List;

public class OrderRequest {

    private Header header;
    private List<Line> line;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Line> getLine() {
        return line;
    }

    public void setLine(List<Line> line) {
        this.line = line;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    private String orderRef;

}
