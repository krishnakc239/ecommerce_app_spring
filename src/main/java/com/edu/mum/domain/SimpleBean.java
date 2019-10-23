package com.edu.mum.domain;

public class SimpleBean {
    private String stringValue = "";
    private int intValue = 1;

    public SimpleBean() {
    }

    public SimpleBean(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public String getValue() {
        return stringValue;
    }

    public void setValue(String value) {
        this.stringValue = value;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "stringValue='" + stringValue + '\'' +
                ", intValue=" + intValue +
                '}';
    }
}
