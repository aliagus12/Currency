package com.aliagushutapea.convertion.model;

/**
 * Created by ali on 07/01/18.
 */

public class CurrencyModel {
    String currencyId;
    String currencyName;
    String currencyCountryPath;
    String nameCountry;
    String currencyImagePath;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCountryPath() {
        return currencyCountryPath;
    }

    public void setCurrencyCountryPath(String currencyCountryPath) {
        this.currencyCountryPath = currencyCountryPath;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setCountryName(String countryName) {
        this.nameCountry = countryName;
    }

    public String getCurrencyImagePath() {
        return currencyImagePath;
    }

    public void setCurrencyImagePath(String currencyImagePath) {
        this.currencyImagePath = currencyImagePath;
    }

}
