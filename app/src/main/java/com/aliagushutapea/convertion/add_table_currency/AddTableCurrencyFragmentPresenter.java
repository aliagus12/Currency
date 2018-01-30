package com.aliagushutapea.convertion.add_table_currency;

import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import javax.inject.Inject;

/**
 * Created by ali on 27/01/18.
 */

public class AddTableCurrencyFragmentPresenter implements AddTableCurrencyFragmentContract.Presenter{

    AddTableCurrencyFragmentContract.View view;
    private DatabaseManagerHelper databaseManagerHelper;

    @Inject
    public AddTableCurrencyFragmentPresenter(
            AddTableCurrencyFragmentContract.View view,
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
    public void addTableToDatabase(String nameTable) {
        boolean isTableExists = databaseManagerHelper.isTableKeyExists(nameTable);
        if (isTableExists){
            view.makeToast(R.string.table_exists);
        } else {
            databaseManagerHelper.addNewTableToDatabase(nameTable);
            view.dismissDialog();
        }
    }
}
