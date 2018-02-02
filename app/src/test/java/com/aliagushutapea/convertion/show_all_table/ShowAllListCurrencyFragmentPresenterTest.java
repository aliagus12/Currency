package com.aliagushutapea.convertion.show_all_table;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 31/01/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowAllListCurrencyFragmentPresenterTest {

    ShowAllListCurrencyFragmentPresenter mPresenter;
    ShowAllListCurrencyFragmentContract.View view;
    DatabaseManagerHelper databaseManagerHelper;
    Context context;
    CurrencyModel currencyModel;
    private static final String TABLE_CURRENCY_TARGET = "currencyTarget";
    private static final String TABLE_CURRENCY_RESULT = "currencyResult";
    public static final String COL_CURRENCY_ID = "currencyId";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        currencyModel = new CurrencyModel();
        databaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(ShowAllListCurrencyFragmentContract.View.class);
        mPresenter = new ShowAllListCurrencyFragmentPresenter(
                view,
                databaseManagerHelper
        );
    }

    @Test
    public void shouldSetAdapter() throws Exception {
        List<CurrencyModel> modelList = new ArrayList<>();
        modelList.add(new CurrencyModel());
        modelList.add(new CurrencyModel());
        List<Integer> listType = new ArrayList<>();
        listType.add(1);
        listType.add(1);
        Mockito.when(databaseManagerHelper.fetchAllCurrencyModelFromDatabaseManagerHelper(TABLE_CURRENCY, COL_CURRENCY_ID))
                .thenReturn(modelList);

        mPresenter.loadListCurrency();
        Mockito.verify(view).setAdapterCurrency(modelList, listType);
    }

    @Test
    public void shouldSaveCurrencyToTableTarget() throws Exception {
        String filter = "target";
        mPresenter.saveCurrencyModelToDatabase(currencyModel, filter);
        Mockito.verify(databaseManagerHelper).saveCurrencyToTargetOrResult(
                currencyModel,
                TABLE_CURRENCY_TARGET,
                COL_CURRENCY_ID
        );
    }

    @Test
    public void shouldSaveCurrencyToTableResult() throws Exception {
        String filter = "result";
        mPresenter.saveCurrencyModelToDatabase(currencyModel, filter);
        Mockito.verify(databaseManagerHelper).saveCurrencyToTargetOrResult(
                currencyModel,
                TABLE_CURRENCY_RESULT,
                COL_CURRENCY_ID
        );
    }
}