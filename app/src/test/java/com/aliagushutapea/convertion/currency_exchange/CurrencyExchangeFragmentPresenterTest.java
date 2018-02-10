package com.aliagushutapea.convertion.currency_exchange;

import android.view.View;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

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
public class CurrencyExchangeFragmentPresenterTest {

    CurrencyExchangeFragmentPresenter presenter;
    CurrencyExchangeFragmentContract.View view;
    DatabaseManagerHelper mDatabaseManagerHelper;
    CurrencyModel mCurrencyModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDatabaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(CurrencyExchangeFragmentContract.View.class);
        mCurrencyModel = new CurrencyModel();
        presenter = new CurrencyExchangeFragmentPresenter(

                view,
                mCurrencyModel,
                mDatabaseManagerHelper
        );
    }

    @Test
    public void shouldViewLoadData() throws Exception {
        CurrencyModel target = new CurrencyModel();
        CurrencyModel result = new CurrencyModel();
        Mockito.when(mDatabaseManagerHelper.getCurrencyModelWithoutKey(
                SourceString.TARGET_CURRENCY_COLOMN)
        ).thenReturn(target);
        Mockito.when(mDatabaseManagerHelper.getCurrencyModelWithoutKey(
                SourceString.RESULT_CURRENCY_COLOMN)
        ).thenReturn(result);

        presenter.loadData();
        Mockito.verify(view).loadDataToView(target, result);
    }

    @Test
    public void showListCurrency() throws Exception {
        View view = Mockito.mock(View.class);
        presenter.showListCurrency(view);
        assertTrue(mCurrencyModel.getValueConfiguration().equals("content"));
        Mockito.verify(mDatabaseManagerHelper).saveConfiguration(SourceString.CONFIGURATION_COLOMN, mCurrencyModel);
        Mockito.verify(this.view).showAllCurrency(view);
    }
}