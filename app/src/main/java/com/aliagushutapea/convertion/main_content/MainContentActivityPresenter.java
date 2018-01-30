package com.aliagushutapea.convertion.main_content;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import javax.inject.Inject;

/**
 * Created by ali on 24/01/18.
 */

public class MainContentActivityPresenter implements MainContentActivityContract.Presenter {

    MainContentActivityContract.View viewContent;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;

    @Inject
    public MainContentActivityPresenter(
            MainContentActivityContract.View viewContent,
            CurrencyModel currencyModel,
            DatabaseManagerHelper databaseManagerHelper
    ) {
        this.viewContent = viewContent;
        this.currencyModel = currencyModel;
        this.databaseManagerHelper = databaseManagerHelper;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
        viewContent = null;
    }

    public void addCurrency() {
        viewContent.attachFragmentAddCurrency();
    }

    public void goToInpectionDatabase(){
        viewContent.inspecDatabase();
    }
}
