package com.aliagushutapea.convertion;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by ali on 01/02/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

   MainActivityPresenter mPresenter;
   MainActivityContract.View mView;
   Context context;
   DatabaseManagerHelper databaseManagerHelper;
   CurrencyModel currencyModel;
    public static final String TABLE_ALL_CURRENCY = "all_currency";
    public static final String COL_SYMBOL = "Symbol";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        mView = Mockito.mock(MainActivityContract.View.class);
        currencyModel = new CurrencyModel();
        databaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        mPresenter = new MainActivityPresenter(
                mView,
                currencyModel,
                databaseManagerHelper,
                context
        );
    }

    @Test
    public void shouldInsertDataToDatabase() throws Exception {
        mPresenter.insertDataToDatabase();
        assertTrue(databaseManagerHelper.fetchAllCurrencyModelFromDatabaseManagerHelper(TABLE_ALL_CURRENCY, COL_SYMBOL).size() == 10);
    }
}