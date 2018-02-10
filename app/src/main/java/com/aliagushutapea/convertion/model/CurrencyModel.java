package com.aliagushutapea.convertion.model;

/**
 * Created by ali on 07/01/18.
 */

public class CurrencyModel {
    String mSymbol;
    String mName;
    String mCountry;
    String mSymbolNative;
    String mImageCountry;
    String mImageCurrency;
    String keyConfiguration;
    String valueConfiguration;
    String mId;
    String mResult;

    public String getKeyConfiguration() {
        return keyConfiguration;
    }

    public void setKeyConfiguration(String keyConfiguration) {
        this.keyConfiguration = keyConfiguration;
    }

    public String getValueConfiguration() {
        return valueConfiguration;
    }

    public void setValueConfiguration(String valueConfiguration) {
        this.valueConfiguration = valueConfiguration;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getSymbolNative() {
        return mSymbolNative;
    }

    public void setSymbolNative(String mSymbolNative) {
        this.mSymbolNative = mSymbolNative;
    }

    public String getImageCountry() {
        return mImageCountry;
    }

    public void setImageCountry(String mImageCountry) {
        this.mImageCountry = mImageCountry;
    }

    public String getImageCurrency() {
        return mImageCurrency;
    }

    public void setImageCurrency(String mImageCurrency) {
        this.mImageCurrency = mImageCurrency;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String mResult) {
        this.mResult = mResult;
    }
}
