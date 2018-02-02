package com.aliagushutapea.convertion.show_all_table;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ali on 27/01/18.
 */

public class ShowAllListCurrencyFragmentPresenter implements ShowAllListCurrencyFragmentContract.Presenter{

    private static final String TAG = ShowAllListCurrencyFragment.class.getSimpleName();
    ShowAllListCurrencyFragmentContract.View view;
    private DatabaseManagerHelper databaseManagerHelper;

    @Inject
    public ShowAllListCurrencyFragmentPresenter(
            ShowAllListCurrencyFragmentContract.View view,
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
        List<CurrencyModel> currencyModelList = databaseManagerHelper
                .getAllCurrencyFromDatabase(SourceString.ALL_CURRENCY_COLOMN);
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
                databaseManagerHelper.saveCurrencyToTargetOrResult(
                        SourceString.TARGET_CURRENCY_COLOMN,
                        currencyModel
                );
                break;
            case "result":
                databaseManagerHelper.saveCurrencyToTargetOrResult(
                        SourceString.RESULT_CURRENCY_COLOMN,
                        currencyModel
                );
                break;
        }
    }

}
