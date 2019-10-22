package com.edu.mum.domain;

public class ShipCountry {
    String countryCode;
    String countryName;
    Double cost;

    public ShipCountry() {
    }

    public ShipCountry(String countryCode, String countryName, Double cost) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.cost = cost;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ShipCountry{" +
                "countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
