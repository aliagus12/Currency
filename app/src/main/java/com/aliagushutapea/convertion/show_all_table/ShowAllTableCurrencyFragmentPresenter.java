package com.aliagushutapea.convertion.show_all_table;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ali on 27/01/18.
 */

public class ShowAllTableCurrencyFragmentPresenter implements ShowAllTableCurrencyFragmentContract.Presenter{

    ShowAllTableCurrencyFragmentContract.View view;
    private DatabaseManagerHelper databaseManagerHelper;
    private static final String TABLE_CURRENCY_TARGET = "currencyTarget";
    private static final String TABLE_CURRENCY_RESULT = "currencyResult";
    public static final String COL_CURRENCY_ID = "currencyId";

    @Inject
    public ShowAllTableCurrencyFragmentPresenter(
            ShowAllTableCurrencyFragmentContract.View view,
            DatabaseManagerHelper databaseManagerHelper
    ) {
        this.view = view;
        this.databaseManagerHelper = databaseManagerHelper;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loadListCurrency() {
        List<CurrencyModel> currencyModelList = databaseManagerHelper.fetchAllCurrencyModelFromDatabaseManagerHelper();
        List<Integer> listType = new ArrayList<>();
        for (int a = 0; a < currencyModelList.size(); a++) {
            listType.add(1);
        }
        view.setAdapterCurrency(currencyModelList, listType);
    }

    @Override
    public void saveCurrencyModelToDatabase(CurrencyModel currencyModel, String filter) {
        switch (filter){
            case "target":
                databaseManagerHelper.saveCurrencyToTable(
                        currencyModel,
                        TABLE_CURRENCY_TARGET,
                        COL_CURRENCY_ID
                );
                break;
            case "result":
                databaseManagerHelper.saveCurrencyToTable(
                        currencyModel,
                        TABLE_CURRENCY_RESULT,
                        COL_CURRENCY_ID
                );
                break;
        }
    }

}
