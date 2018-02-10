package com.aliagushutapea.convertion.show_all_table;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.CurrencyUtils;
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
    private CurrencyUtils currencyUtils;

    @Inject
    public ShowAllListCurrencyFragmentPresenter(
            ShowAllListCurrencyFragmentContract.View view,
            DatabaseManagerHelper databaseManagerHelper,
            CurrencyUtils currencyUtils
    ) {
        this.view = view;
        this.databaseManagerHelper = databaseManagerHelper;
        this.currencyUtils = currencyUtils;
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

    @Override
    public void querySearch(String newText) {
        List<CurrencyModel> currencyModelLists = databaseManagerHelper.getAllCurrencyFromDatabase(SourceString.ALL_CURRENCY_COLOMN);
        //CurrencyUtils currencyUtils = new CurrencyUtils();
        List<CurrencyModel> listCurrencyModelFiltered = currencyUtils.filter(currencyModelLists, newText);
        List<Integer> listType = new ArrayList<>();
        for (int a = 0; a < listCurrencyModelFiltered.size(); a++) {
            listType.add(1);
        }
        view.refreshAdapter(listCurrencyModelFiltered, listType);
    }
}
