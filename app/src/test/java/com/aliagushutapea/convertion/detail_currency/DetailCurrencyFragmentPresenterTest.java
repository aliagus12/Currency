package com.aliagushutapea.convertion.detail_currency;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

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
        String id = "fake id";
        String symbol = "fake symbol";
        String name = "fake name";
        String countryName = "fake countryName";
        String symbolNative = "fake symbol native";
        String imageCountry = "fake imageCountry";
        String imageCurrency = "fake imageCurrency";
        mPresenter.saveDataCurrencyToDataBase(
                id,
                symbol,
                name,
                countryName,
                symbolNative,
                imageCountry,
                imageCurrency
        );
        assertTrue(mCurrencyModel.getId().equals(id));
        assertTrue(mCurrencyModel.getSymbol().equals(symbol));
        assertTrue(mCurrencyModel.getName().equals(name));
        assertTrue(mCurrencyModel.getCountry().equals(countryName));
        assertTrue(mCurrencyModel.getSymbolNative().equals(symbolNative));
        assertTrue(mCurrencyModel.getImageCountry().equals(imageCountry));
        assertTrue(mCurrencyModel.getImageCurrency().equals(imageCurrency));
        Mockito.verify(mView).dismissDialog();
        Mockito.verify(mDatabaseManagerHelper).insertCurrencyToDataBase(
                SourceString.ALL_CURRENCY_COLOMN,
                mCurrencyModel
        );
    }
}