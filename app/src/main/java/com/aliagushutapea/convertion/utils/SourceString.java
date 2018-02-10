package com.aliagushutapea.convertion.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 01/02/18.
 */

public class SourceString {
    //allCurrency
    public static final String TABLE_ALL_CURRENCY = "all_currency";
    public static final String COL_NO = "No";
    public static final String COL_ID = "Id";
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

    //targetresult
    static final String COL_NAMECURRENCY_THAT_WANT_TO_COMPARED = "toCurrency";
    static final String COL_VALUE_THAT_COMPARED = "value";

    public static final String[] ALL_CURRENCY_COLOMN = new String[]{
            TABLE_ALL_CURRENCY,
            COL_ID,
            COL_SYMBOL,
            COL_NAME,
            COL_COUNTRY,
            COL_SYMBOL_NATIVE,
            COL_IMAGE_COUNTRY,
            COL_IMAGE_CURRENCY
    };

    public static final String[] TARGET_CURRENCY_COLOMN = new String[]{
            TABLE_TARGET,
            COL_ID,
            COL_SYMBOL,
            COL_NAME,
            COL_COUNTRY,
            COL_SYMBOL_NATIVE,
            COL_IMAGE_COUNTRY,
            COL_IMAGE_CURRENCY
    };

    public static final String[] RESULT_CURRENCY_COLOMN = new String[]{
            TABLE_RESULT,
            COL_ID,
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

    public static final String[] ARRAY_KEYS = new String[]{
            "ZWL", "ZMW", "ZAR", "YER", "XRP", "XPF", "XOF", "XMR", "XLM", "XCD",
            "XAF", "WST", "VUV", "VND", "VEF", "UZS", "UGX", "UYU", "UAH", "TZS",
            "TWD", "TTD", "TRY", "TRX", "TOP", "TND", "TMT", "TJS", "THB", "SZL",
            "SYP", "SVC", "STD", "SRD", "SOS", "SLL", "SIT", "SHP", "SGD", "SEK",
            "SDG", "SCR", "SBD", "RWF", "SAR", "RSD", "RON", "QAR", "PYG", "PPC",
            "PLN", "PHP", "PGK", "PEN", "PAB", "OMR", "NZD", "NVC", "NPR", "NOK",
            "NMC", "NIO", "NGN", "NEO", "NAD", "MZN", "MYR", "MWK", "MVR", "MUR",
            "MRO", "MOP", "MNT", "MMK", "MKD", "MGA", "MDL", "MAD", "LYD", "LVL",
            "TLT", "LTC", "LSL", "LRD", "LKR", "LBP", "LAK", "KYD", "KWD", "KZT",
            "KRW", "KPW", "KMF", "KHR", "KGS", "KES", "JOD", "JMD", "ISK", "IRR",
            "IQD", "IOT", "INR", "ILS", "HUF", "HTG", "HRK", "HNL", "HKD", "GYD",
            "GTQ", "GNF", "GMD", "GIP", "GHS", "GEL", "FKP", "FJD", "ETH", "ETB",
            "ERN", "EOS", "EGP", "DZD", "DSH", "DOP", "DKK", "DJF", "CZK", "CVE",
            "CUP", "CRC", "COP", "CLP", "CLF", "CHF", "CDF", "BZD", "BYR", "BYN",
            "BWP", "BTN", "BTG", "BTC", "BSD", "BSD", "BRL", "BOB", "BND", "BMD",
            "BIF", "BHD", "BDT", "BDD", "BAM", "AZN", "AWG", "ARS", "AOA", "ANG",
            "AMD", "ALL", "AFN", "ADA", "RUB", "AED", "BGN", "CAD", "AUD", "IDR",
            "CNY", "JPY", "GBP", "EUR", "USD", "PKR", "BBD", "MXN"
    };

    public static final List<String> getListKey(){
        List<String> listKey = new ArrayList<>();
        for (String key : ARRAY_KEYS) {
            listKey.add(key);
        }
        return listKey;
    }

    public static final Double[] ARRAY_VALUES = new Double[]{
            332.36, 9.71, 12.02, 249.95, 1.29, 95.96, 530.17, 0.0047, 2.68, 1.67,
            2.7, 530.17, 2.49, 106.05, 22726.92, 24298.0, 8179.7, 3630.0, 28.45, 27.41,
            2245.0, 29.24, 6.72, 3.78, 27.29, 2.23, 2.39, 3.47, 8.81, 31.56,
            11.92, 514.98, 8.75, 19822.0, 7.42, 562.0, 7630.0, 216.49, 0.72, 1.32,
            7.97, 17.99, 13.4, 7.75, 835.75, 3.75, 95.7, 3.77, 3.64, 5581.0,
            2.12, 3.37, 51.44, 3.22, 3.24, 1.0, 0.38, 1.37, 1.07, 102.76,
            7.84, 2.13, 30.95, 359.31, 0.0086, 11.93, 61.3, 3.91, 713.56, 15.57,
            31.42, 350.00, 8.06, 2405.67, 1328.5, 49.6, 3170.0, 16.56, 9.19, 1.33,
            0.62, 3.05, 0.00066, 12.01, 124.59, 154.58, 1510.6, 8273.43, 0.82, 0.3,
            323.38, 1084.96, 900.0, 395.2, 4017.42, 68.48, 101.16, 0.71, 124.46, 100.9,
            38509.62, 1184.0, 0.56, 64.23, 3.48, 251.21, 63.39, 6.03, 23.46, 7.82,
            205.06, 7.34, 9006.0, 47.05, 0.72, 4.46, 2.46, 0.72, 2.01, 0.0012,
            27.21, 14.99, 0.12, 17.62, 113.36, 0.0018, 48.88, 6.02, 176.83, 20.4,
            89.34, 1.0, 570.45, 2828.8, 609.77, 0.022, 0.94, 1565.5, 2.0, 1.99,
            1.99, 9.6, 64.13, 0.011, 0.00013, 1.0, 3.25, 6.86, 1.32, 1.0,
            1750.98, 0.38, 82.96, 2.0, 1.59, 1.7, 1.78, 19.57, 208.67, 1.78,
            481.43, 107.28, 68.65, 2.7, 57.12, 3.67, 1.58, 1.25, 1.27, 13574.66,
            6.27, 109.3, 0.72, 0.81, 1.0, 112.84, 2.0, 18.74
    };

    public static final List<String> getListValue(){
        List<String> listValue = new ArrayList<>();
        for (Double value : ARRAY_VALUES) {
            String valueString = String.valueOf(value);
            listValue.add(valueString);
        }
        return listValue;
    }

    public static final String[] TARGET_RESULT(String tableName) {
        return new String[]{
                tableName,
                COL_NAMECURRENCY_THAT_WANT_TO_COMPARED,
                COL_VALUE_THAT_COMPARED
        };
    }
}
