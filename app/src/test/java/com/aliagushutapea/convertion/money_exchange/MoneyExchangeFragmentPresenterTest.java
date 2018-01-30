package com.aliagushutapea.convertion.money_exchange;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDatabaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(MoneyExchangeFragmentContract.View.class);
        mCurrencyModel = Mockito.mock(CurrencyModel.class);
        presenter = new MoneyExchangeFragmentPresenter(
                view,
                mCurrencyModel,
                mDatabaseManagerHelper
        );
    }

    @Test
    public void shouldShowToast() throws Exception {
        presenter.loadData();
        Mockito.verify(view).makeToast(R.string.load_data_success);
    }
}