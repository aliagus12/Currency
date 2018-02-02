package com.aliagushutapea.convertion.money_exchange;

import android.view.View;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by ali on 25/01/18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({

})
@PowerMockIgnore("javax.net.ssl.*")
public class MoneyExchangeFragmentPresenterTest {

    MoneyExchangeFragmentPresenter presenter;
    MoneyExchangeFragmentContract.View view;
    DatabaseManagerHelper mDatabaseManagerHelper;
    CurrencyModel mCurrencyModel;
    private static final String TABLE_CURRENCY_TARGET = "currencyTarget";
    private static final String TABLE_CURRENCY_RESULT = "currencyResult";
    public static final String TABLE_CONFIGURATION = "configuration";
    public static final String COL_CURRENCY_ID = "currencyId";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDatabaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(MoneyExchangeFragmentContract.View.class);
        mCurrencyModel = new CurrencyModel();
        presenter = new MoneyExchangeFragmentPresenter(
                view,
                mCurrencyModel,
                mDatabaseManagerHelper
        );
    }

    @Test
    public void shouldViewLoadData() throws Exception {
        CurrencyModel target = new CurrencyModel();
        CurrencyModel result = new CurrencyModel();
        Mockito.when(mDatabaseManagerHelper.fetchCurrencyModelFromDatabaseHelper(
                TABLE_CURRENCY_TARGET,
                COL_CURRENCY_ID
        )).thenReturn(target);
        Mockito.when(mDatabaseManagerHelper.fetchCurrencyModelFromDatabaseHelper(
                TABLE_CURRENCY_RESULT,
                COL_CURRENCY_ID
        )).thenReturn(result);

        presenter.loadData();
        Mockito.verify(view).loadDataToView(target, result);
    }

    @Test
    public void showListCurrency() throws Exception {
        View view = Mockito.mock(View.class);

        presenter.showListCurrency(view);
        assertTrue(mCurrencyModel.getValueConfiguration().equals("content"));
        Mockito.verify(mDatabaseManagerHelper).saveConfiguration(mCurrencyModel, TABLE_CONFIGURATION);
        Mockito.verify(this.view).showAllCurrency(view);
    }
}