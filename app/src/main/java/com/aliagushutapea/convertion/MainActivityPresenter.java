package com.aliagushutapea.convertion;

import android.content.Context;
import android.net.Uri;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.SourceString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ali on 06/01/18.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {


    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private MainActivityContract.View view;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;
    private Context context;
    public static final String TABLE_ALL_CURRENCY = "all_currency";
    public static final String COL_SYMBOL = "Symbol";

    @Inject
    public MainActivityPresenter(
            MainActivityContract.View view,
            CurrencyModel currencyModel,
            DatabaseManagerHelper databaseManagerHelper,
            Context context
    ) {
        this.view = view;
        this.currencyModel = currencyModel;
        this.databaseManagerHelper = databaseManagerHelper;
        this.context = context;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void insertDataToDatabase() {
        try {
            JSONArray jsonArrayAllDataCurrency = new JSONArray(getStringFromFileJson(R.raw.all_currency_json_array));
            List<CurrencyModel> listAllCurrency = databaseManagerHelper.getAllCurrencyFromDatabase(
                    SourceString.ALL_CURRENCY_COLOMN
            );
            if (listAllCurrency.size() == 0) {
                List<JSONObject> listAllDataCurrency = new ArrayList<>();
                for (int a = 0; a < jsonArrayAllDataCurrency.length(); a++) {
                    JSONObject jsonObjectData = jsonArrayAllDataCurrency.getJSONObject(a);
                    JSONObject jsonObject = getJsonObjectWithFlag(jsonObjectData);
                    listAllDataCurrency.add(jsonObject);
                }
                databaseManagerHelper.bulkInsertDataCurrencyToDatabase(listAllDataCurrency);
            } else if (listAllCurrency.size() > 0) {
                for (int a = 0; a < jsonArrayAllDataCurrency.length(); a++) {
                    JSONObject jsonObjectData = jsonArrayAllDataCurrency.getJSONObject(a);
                    JSONObject jsonObject = getJsonObjectWithFlag(jsonObjectData);
                    String key = jsonObject.getString("symbol");
                    boolean isExist = databaseManagerHelper.isExists(
                            SourceString.ALL_CURRENCY_COLOMN,
                            key
                    );
                    if (!isExist) {
                        currencyModel.setSymbol(jsonObject.getString("symbol"));
                        currencyModel.setName(jsonObject.getString("name"));
                        currencyModel.setCountry("-");
                        currencyModel.setSymbolNative(jsonObject.getString("symbol_native"));
                        currencyModel.setImageCountry(jsonObject.getString("flag_uri"));
                        currencyModel.setImageCurrency("-");
                        databaseManagerHelper.insertCurrencyToDataBase(
                                SourceString.ALL_CURRENCY_COLOMN,
                                currencyModel
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getJsonObjectWithFlag(JSONObject jsonObjectData) throws JSONException {
        if (jsonObjectData.has("flag")) {
            String flag = jsonObjectData.getString("flag");
            Uri flagsUri = Uri.parse("android.resource://com.aliagushutapea.convertion/raw/" + flag);
            jsonObjectData.put("flag_uri", flagsUri.toString());
        }
        return jsonObjectData;
    }

    public String getStringFromFileJson(int nameFile) throws Exception {
        InputStream is = context.getResources().openRawResource(nameFile);
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
