package com.aliagushutapea.convertion.utils;

/**
 * Created by ali on 01/02/18.
 */

public class SourceString {
    //allCurrency
    public static final String TABLE_ALL_CURRENCY = "all_currency";
    public static final String COL_NO = "No";
    public static final String COL_SYMBOL = "Symbol";
    public static final String COL_NAME = "Name";
    public static final String COL_COUNTRY = "Country";
    public static final String COL_SYMBOL_NATIVE = "Symbol_Native";
    public static final String COL_IMAGE_COUNTRY = "Image_Country";
    public static final String COL_IMAGE_CURRENCY = "Image_Currency";

    //targetCurrency
    public static final String TABLE_TARGET = "Target";

    //resultCurrency
    public static final String TABLE_RESULT = "Result";

    //keyvalue
    public static final String TABLE_CONFIGURATION = "configuration";
    public static final String COL_KEY = "key";
    public static final String COL_VALUE = "value";

    public static final String[] ALL_CURRENCY_COLOMN = new String[]{
            TABLE_ALL_CURRENCY,
            COL_SYMBOL,
            COL_NAME,
            COL_COUNTRY,
            COL_SYMBOL_NATIVE,
            COL_IMAGE_COUNTRY,
            COL_IMAGE_CURRENCY
    };

    public static final String[] TARGET_CURRENCY_COLOMN = new String[]{
            TABLE_TARGET,
            COL_SYMBOL,
            COL_NAME,
            COL_COUNTRY,
            COL_SYMBOL_NATIVE,
            COL_IMAGE_COUNTRY,
            COL_IMAGE_CURRENCY
    };

    public static final String[] RESULT_CURRENCY_COLOMN = new String[]{
            TABLE_RESULT,
            COL_SYMBOL,
            COL_NAME,
            COL_COUNTRY,
            COL_SYMBOL_NATIVE,
            COL_IMAGE_COUNTRY,
            COL_IMAGE_CURRENCY
    };

    public static final String[] CONFIGURATION_COLOMN = new String[]{
            TABLE_CONFIGURATION,
            COL_KEY,
            COL_VALUE
    };
}
