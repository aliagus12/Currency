package com.aliagushutapea.convertion;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.CurrencyUtils;
import com.aliagushutapea.convertion.utils.SourceString;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 01/02/18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({

})
@PowerMockIgnore("javax.net.ssl.*")
public class MainActivityPresenterTest {

    private static final Object TAG = MainActivityPresenterTest.class.getSimpleName();
    MainActivityPresenter mPresenter;
    MainActivityContract.View mView;
    Context context;
    DatabaseManagerHelper databaseManagerHelper;
    CurrencyModel currencyModel;
    CurrencyUtils currencyUtils;
    CurrencyUtils currencyUtilsFake;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        mView = Mockito.mock(MainActivityContract.View.class);
        currencyModel = new CurrencyModel();
        currencyUtilsFake = Mockito.mock(CurrencyUtils.class);
        currencyUtils = new CurrencyUtils(instance, context);
        databaseManagerHelper = Mockito.mock(DatabaseManagerHelper.class);
        mPresenter = new MainActivityPresenter(
                mView,
                currencyModel,
                databaseManagerHelper,
                context,
                currencyUtilsFake
        );
    }

    @Test
    public void shouldInsertDataToDatabase() throws Exception {
        List<CurrencyModel> models = Mockito.mock(ArrayList.class);
        String dataAllCurrency = getStringFromFileJson("new_currency.json");
        JSONArray jsonArrayAllDataCurrency = new JSONArray(dataAllCurrency);

        List<JSONObject> listAllDataCurrency = currencyUtils.
                getListJsonObjectModelWithFlagAndImageCurrency(jsonArrayAllDataCurrency);

        System.out.println(listAllDataCurrency.get(0).getString("name"));

        Mockito.when(currencyUtilsFake.getStringFromFileJson(R.raw.new_currency))
                .thenReturn(dataAllCurrency);

        PowerMockito.whenNew(JSONArray.class)
                .withParameterTypes(String.class)
                .withArguments(Matchers.any())
                .thenReturn(jsonArrayAllDataCurrency);

        Mockito.when(currencyUtilsFake.getListJsonObjectModelWithFlagAndImageCurrency(
                jsonArrayAllDataCurrency
        )).thenReturn(listAllDataCurrency);

        Mockito.when(databaseManagerHelper.getAllCurrencyFromDatabase(
                SourceString.ALL_CURRENCY_COLOMN
        )).thenReturn(models);
        Mockito.when(models.size()).thenReturn(0);

        mPresenter.insertDataToDatabase();
        Mockito.verify(databaseManagerHelper).bulkInsertDataCurrencyToDatabase(listAllDataCurrency);
    }

    public String getStringFromFileJson(String nameFile) throws Exception{
        InputStream is = context.getClass().getClassLoader().getResourceAsStream(nameFile);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String jsonString = writer.toString();
        return jsonString;
    }
}