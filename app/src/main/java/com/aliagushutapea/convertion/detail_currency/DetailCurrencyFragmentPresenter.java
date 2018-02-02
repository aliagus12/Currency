package com.aliagushutapea.convertion.detail_currency;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

import javax.inject.Inject;

/**
 * Created by ali on 25/01/18.
 */

public class DetailCurrencyFragmentPresenter implements DetailCurrencyFragmentContract.Presenter {

    DetailCurrencyFragmentContract.View view;
    CurrencyModel currencyModel;
    public static final String COL_CURRENCY_ID = "currencyId";

    private DatabaseManagerHelper databaseManagerHelper;

    @Inject
    public DetailCurrencyFragmentPresenter(
            DetailCurrencyFragmentContract.View view,
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
    public void saveDataCurrencyToDataBase(
            String idCurrency,
            String nameCurrency,
            String countryName,
            String imagePathCountry,
            String imagePathCurrency
    ) {
        /*currencyModel.setCountry(countryName);
        currencyModel.setImageCurrency(imagePathCurrency);
        boolean isExist = databaseManagerHelper.isCurrencyIdExists(
                idCurrency,
                "currency",
                COL_CURRENCY_ID
        );
        if (isExist) {
            view.makeToast(R.string.data_exists);
        } else {
            databaseManagerHelper.addCurrency(currencyModel);
            view.dismissDialog();
        }*/

    }
}

