package com.aliagushutapea.convertion.add_currency;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import javax.inject.Inject;

/**
 * Created by ali on 25/01/18.
 */

public class AddCurrencyFragmentPresenter implements AddCurrencyFragmentContract.Presenter {

    AddCurrencyFragmentContract.View view;
    CurrencyModel currencyModel;

    private DatabaseManagerHelper databaseManagerHelper;

    @Inject
    public AddCurrencyFragmentPresenter(
            AddCurrencyFragmentContract.View view,
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
        currencyModel.setCurrencyId(idCurrency);
        currencyModel.setCurrencyName(nameCurrency);
        currencyModel.setCountryName(countryName);
        currencyModel.setCurrencyCountryPath(imagePathCountry);
        currencyModel.setCurrencyImagePath(imagePathCurrency);
        boolean isExist = databaseManagerHelper.isCurrencyIdExists(idCurrency, "currency");
        if (isExist) {
            view.makeToast(R.string.data_exists);
        } else {
            databaseManagerHelper.addCurrency(currencyModel);
            view.dismissDialog();
        }

    }
}
