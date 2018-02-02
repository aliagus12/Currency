package com.aliagushutapea.convertion.money_exchange;

import android.view.View;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

import javax.inject.Inject;

/**
 * Created by ali on 07/01/18.
 */

public class MoneyExchangeFragmentPresenter implements MoneyExchangeFragmentContract.Presenter {

    private static final String TAG = MoneyExchangeFragmentPresenter.class.getSimpleName();
    private MoneyExchangeFragmentContract.View view;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;

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

    @Override
    public void loadData() {
        CurrencyModel target = databaseManagerHelper
                .getCurrencyModelWithoutKey(SourceString.TARGET_CURRENCY_COLOMN);
        CurrencyModel result = databaseManagerHelper
                .getCurrencyModelWithoutKey(SourceString.RESULT_CURRENCY_COLOMN);
        view.loadDataToView(target, result);
    }

    public void showListCurrency(View view){
        currencyModel.setKeyConfiguration("configuration");
        currencyModel.setValueConfiguration("content");
        databaseManagerHelper.saveConfiguration(
                SourceString.CONFIGURATION_COLOMN,
                currencyModel
        );
        this.view.showAllCurrency(view);
    }
}
