package com.aliagushutapea.convertion.detail_currency;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

import javax.inject.Inject;

/**
 * Created by ali on 25/01/18.
 */

public class DetailCurrencyFragmentPresenter implements DetailCurrencyFragmentContract.Presenter {

    DetailCurrencyFragmentContract.View view;
    CurrencyModel currencyModel;
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
            String id,
            String symbol,
            String name,
            String countryName,
            String symbolNative,
            String imageCountry,
            String imagePathCurrency
    ) {
        currencyModel.setId(id);
        currencyModel.setSymbol(symbol);
        currencyModel.setName(name);
        currencyModel.setCountry(countryName);
        currencyModel.setSymbolNative(symbolNative);
        currencyModel.setImageCountry(imageCountry);
        currencyModel.setImageCurrency(imagePathCurrency);
        databaseManagerHelper.insertCurrencyToDataBase(
                SourceString.ALL_CURRENCY_COLOMN,
                currencyModel
        );
        view.dismissDialog();

    }
}

