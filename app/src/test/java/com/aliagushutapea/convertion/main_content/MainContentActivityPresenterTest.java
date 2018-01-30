package com.aliagushutapea.convertion.main_content;

import android.content.Context;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by ali on 25/01/18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
        DatabaseManagerHelper.class
})
@PowerMockIgnore("javax.net.ssl.*")
public class MainContentActivityPresenterTest {

    MainContentActivityPresenter mPresenter;
    MainContentActivityContract.View view;
    CurrencyModel currencyModel;
    DatabaseManagerHelper databaseManagerHelper;
    Context context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        databaseManagerHelper = PowerMockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(MainContentActivityContract.View.class);
        currencyModel = new CurrencyModel();
        mPresenter = new MainContentActivityPresenter(
                view,
                currencyModel,
                databaseManagerHelper
        );
        mockStatic(DatabaseManagerHelper.class);
    }

    @Test
    public void shouldShowFragmentAddCurrency() throws Exception {
        mPresenter.addCurrency();
        Mockito.verify(view).attachFragmentAddCurrency();
    }
}