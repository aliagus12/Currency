package com.aliagushutapea.convertion.add_currency;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.detail_currency.DetailCurrencyFragmentContract;
import com.aliagushutapea.convertion.detail_currency.DetailCurrencyFragmentPresenter;
import com.aliagushutapea.convertion.model.CurrencyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by ali on 31/01/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailCurrencyFragmentPresenterTest {

    DetailCurrencyFragmentPresenter mPresenter;
    DetailCurrencyFragmentContract.View mView;
    DatabaseManagerHelper mDatabaseManagerHelper;
    CurrencyModel mCurrencyModel;
    Context mContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mContext = Mockito.mock(Context.class);
        mView = Mockito.mock(DetailCurrencyFragmentContract.View.class);
        mCurrencyModel = new CurrencyModel();
        mDatabaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        mPresenter = new DetailCurrencyFragmentPresenter(
                mView,
                mCurrencyModel,
                mDatabaseManagerHelper
        );
    }

    @Test
    public void shouldSaveDataCurrencyToDatabase() throws Exception {
        String idCurrency = "fake idCurrency";
        String nameCurrency = "fake nameCurrency";
        String countryName = "fake countryName";
        String imagePathCountry = "fake imagePathCountry";
        String imagePathCurrency = "fake imagePathCurrency";
        Mockito.when(mDatabaseManagerHelper.isCurrencyIdExists(idCurrency, "currency", COL_CURRENCY_ID))
                .thenReturn(false);

        mPresenter.saveDataCurrencyToDataBase(
                idCurrency,
                nameCurrency,
                countryName,imagePathCountry,
                imagePathCurrency
        );
        assertTrue(mCurrencyModel.getCurrencyId().equals(idCurrency));
        assertTrue(mCurrencyModel.getNameCountry().equals(countryName));
        assertTrue(mCurrencyModel.getCurrencyName().equals(nameCurrency));
        assertTrue(mCurrencyModel.getCurrencyImagePath().equals(imagePathCurrency));
        assertTrue(mCurrencyModel.getCurrencyCountryPath().equals(imagePathCountry));
        Mockito.verify(mView).dismissDialog();
        Mockito.verify(mDatabaseManagerHelper).addCurrency(mCurrencyModel);
    }
}