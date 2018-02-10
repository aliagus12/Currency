package com.aliagushutapea.convertion.show_all_table;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.CurrencyUtils;
import com.aliagushutapea.convertion.utils.SourceString;

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
    CurrencyUtils currencyUtils;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        currencyModel = new CurrencyModel();
        currencyUtils = new CurrencyUtils(instance, context);
        databaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        view = Mockito.mock(ShowAllListCurrencyFragmentContract.View.class);
        mPresenter = new ShowAllListCurrencyFragmentPresenter(
                view,
                databaseManagerHelper,
                currencyUtils
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
        Mockito.when(databaseManagerHelper.getAllCurrencyFromDatabase(SourceString.ALL_CURRENCY_COLOMN))
                .thenReturn(modelList);

        mPresenter.loadListCurrency();
        Mockito.verify(view).setAdapterCurrency(modelList, listType);
    }

    @Test
    public void shouldSaveCurrencyToTableTarget() throws Exception {
        String filter = "target";
        mPresenter.saveCurrencyModelToDatabase(currencyModel, filter);
        Mockito.verify(databaseManagerHelper).saveCurrencyToTargetOrResult(
                SourceString.TARGET_CURRENCY_COLOMN,
                currencyModel
        );
    }

    @Test
    public void shouldSaveCurrencyToTableResult() throws Exception {
        String filter = "result";
        mPresenter.saveCurrencyModelToDatabase(currencyModel, filter);
        Mockito.verify(databaseManagerHelper).saveCurrencyToTargetOrResult(
                SourceString.RESULT_CURRENCY_COLOMN,
                currencyModel
        );
    }
}