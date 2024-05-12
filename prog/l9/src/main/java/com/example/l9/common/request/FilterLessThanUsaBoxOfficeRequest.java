package com.example.l9.common.request;

public class FilterLessThanUsaBoxOfficeRequest extends Request {
    private double box;
    
    public FilterLessThanUsaBoxOfficeRequest(double b) {
        command = "filter_less_than_usa_box_office";
        box = b;
    }

    public double getBox() {
        return box;
    }
}
