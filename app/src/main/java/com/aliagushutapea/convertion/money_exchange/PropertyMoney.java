package com.aliagushutapea.convertion.money_exchange;

/**
 * Created by ali on 07/01/18.
 */

public class PropertyMoney {


    private static PropertyMoney instance;

    public static PropertyMoney getInstance() {
        if (instance == null){
            instance = new PropertyMoney();
        }
        return instance;
    }
}
