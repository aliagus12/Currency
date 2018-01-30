package com.aliagushutapea.convertion.money_exchange;

import android.view.View;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

import javax.inject.Inject;

/**
 * Created by ali on 07/01/18.
 */

public class MoneyExchangeFragmentPresenter implements MoneyExchangeFragmentContract.Presenter {

    private MoneyExchangeFragmentContract.View view;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;
    private static final String TABLE_CURRENCY_TARGET = "currencyTarget";
    private static final String TABLE_CURRENCY_RESULT = "currencyResult";
    public static final String COL_CURRENCY_ID = "currencyId";

    @Inject
    public MoneyExchangeFragmentPresenter(
            MoneyExchangeFragmentContract.View view,
            CurrencyModel currencyModel,
            DatabaseManagerHelper databaseManagerHelper
    ) {
        this.view = view;
        this.currencyModel = currencyModel;
        this.databaseManagerHelper = databaseManagerHelper;
    }


    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public void showListCurrency(View view){
        this.view.showAllCurrency(view);
    }

    @Override
    public void loadData() {
        CurrencyModel target = databaseManagerHelper
                .fetchCurrencyModelFromDatabaseHelper(
                        TABLE_CURRENCY_TARGET,
                        COL_CURRENCY_ID
                );
        CurrencyModel result = databaseManagerHelper
                .fetchCurrencyModelFromDatabaseHelper(
                        TABLE_CURRENCY_RESULT,
                        COL_CURRENCY_ID
                );
        view.loadDataToView(target, result);
    }
}
