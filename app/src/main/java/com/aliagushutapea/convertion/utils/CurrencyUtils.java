package com.aliagushutapea.convertion.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;

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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ali on 05/02/18.
 */

public class CurrencyUtils {

    private static final String TAG = CurrencyUtils.class.getSimpleName();
    private DatabaseManagerHelper databaseManagerHelper;
    Context context;

    public CurrencyUtils(DatabaseManagerHelper instance, Context context) {
        databaseManagerHelper = instance;
        this.context = context;
    }

    public List<CurrencyModel> filter(List<CurrencyModel> listModel, String query) {
        query = query.toLowerCase();
        final List<CurrencyModel> filteredModelList = new ArrayList<>();
        for (CurrencyModel model : listModel) {
            final String text = model.getName().toLowerCase();
            final String counteryText = model.getCountry().toLowerCase();
            if (text.startsWith(query) || text.contains(query) || counteryText.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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

    public JSONObject getJsonObjectWithFlagAndCountry(JSONObject jsonObjectData) throws JSONException {
        String id = jsonObjectData.getString("id").toLowerCase();
        String nameCurrency = jsonObjectData.getString("name").toLowerCase();
        nameCurrency = nameCurrency.trim().replaceAll("\\s+", "_");
        String symbolNative;
        if (jsonObjectData.has("symbol_native")) {
            symbolNative = jsonObjectData.getString("symbol_native");
        } else {
            symbolNative = jsonObjectData.getString("symbol");
        }
        Uri flagsUri = Uri.parse("android.resource://com.aliagushutapea.convertion/raw/" + id);
        Uri currencyUri = Uri.parse("android.resource://com.aliagushutapea.convertion/raw/" + nameCurrency);
        jsonObjectData.put("flag_uri", String.valueOf(flagsUri));
        jsonObjectData.put("currency_uri", String.valueOf(currencyUri));
        jsonObjectData.remove("symbol_native");
        jsonObjectData.put("symbol_native", symbolNative);
        return jsonObjectData;
    }

    public List<JSONObject> getListJsonObjectModelWithFlagAndImageCurrency(JSONArray jsonArray) throws JSONException {
        List<JSONObject> list = new ArrayList<>();
        for (int a = 0; a < jsonArray.length(); a++) {
            JSONObject jsonObjectData = jsonArray.getJSONObject(a);
            JSONObject jsonObject = getJsonObjectWithFlagAndCountry(jsonObjectData);
            list.add(jsonObject);
        }
        return list;
    }

    public JSONObject getJsonObjectData(List<String> listKeys) throws JSONException {
        //listKeys 201
        JSONObject jsonObject = getJsonObjectAllData();
        JSONObject finalJsonObjectAllDataCurrency = new JSONObject();
        for (String keyNameTable : listKeys) {
            if (jsonObject.has(keyNameTable)) {
                boolean isTableExists = databaseManagerHelper.isTableKeyExists("_" + keyNameTable);
                if (isTableExists) {
                    //Log.d(TAG, "keyIsExist " + keyNameTable);
                } else {
                    databaseManagerHelper.addNewTableToDatabase("_" + keyNameTable);
                    String baseValueString = jsonObject.getString(keyNameTable);
                    float baseValueFloat = Float.parseFloat(baseValueString);
                    JSONArray jsonArrayDataTable = new JSONArray();
                    List<String> listKeysItemField = new ArrayList<>();
                    for (String keyDataItem : listKeys) {
                        if (jsonObject.has(keyDataItem)) {
                            listKeysItemField.add(keyDataItem);
                            JSONObject jsonObjectData = new JSONObject();
                            String stringDataValueItem = jsonObject.getString(keyDataItem);
                            float floatValueItem = Float.parseFloat(stringDataValueItem);
                            floatValueItem = floatValueItem / baseValueFloat;
                            Log.d(TAG, "result " + floatValueItem);
                            String valueItem = String.format("%.2f", floatValueItem);
                            jsonObjectData.put(keyDataItem, valueItem);
                            jsonArrayDataTable.put(jsonObjectData);
                        }
                    }
                    databaseManagerHelper.bulkInsertDataValueCurrencyToDatabase(("_" + keyNameTable), jsonArrayDataTable, listKeysItemField);
                }
                /*String baseValueString = jsonObject.getString(keyNameTable);
                float baseValueFloat = Float.parseFloat(baseValueString);
                JSONArray jsonArrayDataTable = new JSONArray();
                for (String keyDataItem : listKeys) {
                    if (jsonObject.has(keyDataItem)) {
                        JSONObject jsonObjectData = new JSONObject();
                        String stringDataValueItem = jsonObject.getString(keyDataItem);
                        float floatValueItem = Float.parseFloat(stringDataValueItem);
                        floatValueItem = floatValueItem / baseValueFloat;
                        String valueItem = String.format("%.2f", floatValueItem);
                        jsonObjectData.put(keyDataItem, valueItem);
                        jsonArrayDataTable.put(jsonObjectData);
                    }
                }
                databaseManagerHelper.bulkInsertDataValueCurrencyToDatabase(jsonArrayDataTable);*/
                //finalJsonObjectAllDataCurrency.put(keyNameTable, jsonArrayDataTable);
            } else if (!jsonObject.has(keyNameTable)) {
                //Log.d(TAG, "notNaveKey " + keyNameTable);
            }
        }
        return finalJsonObjectAllDataCurrency;
    }

    //dataCurrency awal json
    private JSONObject getJsonObjectAllData() throws JSONException {
        List<String> listKeys = SourceString.getListKey();
        List<String> listValues = SourceString.getListValue();
        JSONObject jsonObject = new JSONObject();
        for (int a = 0; a < listValues.size(); a++) {
            String key = listKeys.get(a);
            String value = listValues.get(a);
            jsonObject.put(key, value);
        }
        for (String key : listKeys) {
            if (!jsonObject.has(key)) {
                Log.d(TAG, "Has no Key" + key);
            }
        }
        Log.d(TAG, "length " + jsonObject.length());
        return jsonObject;
    }

    public Observable getObservableInsertDataToDatabase(final CurrencyModel currencyModel) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                try {
                    String dataAllCurrency = getStringFromFileJson(R.raw.new_currency);
                    final JSONArray jsonArrayAllDataCurrency = new JSONArray(dataAllCurrency);
                    List<CurrencyModel> listAllCurrency = databaseManagerHelper.getAllCurrencyFromDatabase(
                            SourceString.ALL_CURRENCY_COLOMN
                    );
                    if (listAllCurrency.size() == 0) {
                        List<JSONObject> listAllDataCurrency = getListJsonObjectModelWithFlagAndImageCurrency(jsonArrayAllDataCurrency);
                        databaseManagerHelper.bulkInsertDataCurrencyToDatabase(listAllDataCurrency);
                        emitter.onNext("");
                        emitter.onComplete();
                    } else if (listAllCurrency.size() > 0) {
                        for (int a = 0; a < jsonArrayAllDataCurrency.length(); a++) {
                            JSONObject jsonObjectData = jsonArrayAllDataCurrency.getJSONObject(a);
                            JSONObject jsonObject = getJsonObjectWithFlagAndCountry(jsonObjectData);
                            String id = jsonObject.getString("id");
                            boolean isExist = databaseManagerHelper.isExists(
                                    SourceString.ALL_CURRENCY_COLOMN,
                                    id
                            );
                            if (!isExist) {
                                currencyModel.setId(id);
                                currencyModel.setSymbol(jsonObject.getString("symbol"));
                                currencyModel.setName(jsonObject.getString("name"));
                                currencyModel.setCountry(jsonObject.getString("country"));
                                currencyModel.setSymbolNative(jsonObject.getString("symbol_native"));
                                currencyModel.setImageCountry(jsonObject.getString("flag_uri"));
                                currencyModel.setImageCurrency(jsonObject.getString("currency_uri"));
                                databaseManagerHelper.insertCurrencyToDataBase(
                                        SourceString.ALL_CURRENCY_COLOMN,
                                        currencyModel
                                );
                            }
                        }
                        emitter.onNext("");
                        emitter.onComplete();
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<JSONObject> getObservableInsertDataToDatabase() {
        return Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(final ObservableEmitter<JSONObject> emitter) throws Exception {
                List<String> listStringKeys = getListKeys();
                JSONObject jsonObjectAll = getJsonObjectData(listStringKeys);
                emitter.onNext(jsonObjectAll);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    private List<String> getListKeys() {
        List<CurrencyModel> listAllCurrency = databaseManagerHelper.getAllCurrencyFromDatabase(
                SourceString.ALL_CURRENCY_COLOMN
        );
        List<String> listKeys = new ArrayList<>();
        for (int a = 0; a < listAllCurrency.size(); a++) {
            CurrencyModel model = listAllCurrency.get(a);
            String name = model.getSymbol();
            listKeys.add(name);
        }
        return listKeys;
    }


    public String getJsonObjectData1(List<String> listKeys) throws JSONException {
        //listKeys 201
        JSONObject jsonObject = getJsonObjectAllData();
        JSONObject finalJsonObjectAllDataCurrency = new JSONObject();
        for (String keyNameTable : listKeys) {
            if (jsonObject.has(keyNameTable)) {
                boolean isTableExists = databaseManagerHelper.isTableKeyExists("_" + keyNameTable);
                if (isTableExists) {
                    Log.d(TAG, "keyIsExist " + keyNameTable);
                } else {
                    databaseManagerHelper.addNewTableToDatabase("_" + keyNameTable);
                }
            }
                /*String baseValueString = jsonObject.getString(keyNameTable);
                float baseValueFloat = Float.parseFloat(baseValueString);
                JSONArray jsonArrayDataTable = new JSONArray();
                for (String keyDataItem : listKeys) {
                    if (jsonObject.has(keyDataItem)) {
                        JSONObject jsonObjectData = new JSONObject();
                        String stringDataValueItem = jsonObject.getString(keyDataItem);
                        float floatValueItem = Float.parseFloat(stringDataValueItem);
                        floatValueItem = floatValueItem / baseValueFloat;
                        String valueItem = String.format("%.2f", floatValueItem);
                        jsonObjectData.put(keyDataItem, valueItem);
                        jsonArrayDataTable.put(jsonObjectData);
                    }
                }
                finalJsonObjectAllDataCurrency.put(keyNameTable, jsonArrayDataTable);
            } else if (!jsonObject.has(keyNameTable)) {
                Log.d(TAG, "notNaveKey " + keyNameTable);
            }*/
        }
        return null;
    }

    public Observable getObservableNewTable(JSONObject jsonObject) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {

            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable getObservableInsertData(final JSONObject jsonObject) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                List<String> listKeys = getListKeys();
                JSONArray jsonArrayDataTable = new JSONArray();
                for (String keyNameTable : listKeys) {
                    String baseValueString = jsonObject.getString(keyNameTable);
                    float baseValueFloat = Float.parseFloat(baseValueString);
                    for (String keyDataItem : listKeys) {
                        if (jsonObject.has(keyDataItem)) {
                            JSONObject jsonObjectData = new JSONObject();
                            String stringDataValueItem = jsonObject.getString(keyDataItem);
                            float floatValueItem = Float.parseFloat(stringDataValueItem);
                            floatValueItem = floatValueItem / baseValueFloat;
                            String valueItem = String.format("%.2f", floatValueItem);
                            jsonObjectData.put(keyDataItem, valueItem);
                            jsonArrayDataTable.put(jsonObjectData);
                        }
                    }
                }
                Log.d(TAG, "ini baru " + jsonArrayDataTable);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<String> getObservableFetchData(final String number) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    CurrencyModel modelTarget = databaseManagerHelper.getCurrencyModelWithoutKey(SourceString.TARGET_CURRENCY_COLOMN);
                    CurrencyModel modelResult = databaseManagerHelper.getCurrencyModelWithoutKey(SourceString.RESULT_CURRENCY_COLOMN);
                    String symbolResult = modelResult.getSymbol();
                    String symbolAsNameTable = modelTarget.getSymbol();
                    symbolAsNameTable = "_" + symbolAsNameTable;
                    JSONObject resultAll = databaseManagerHelper.
                            getAllCurrencyValueFromDatabase(
                                    SourceString.TARGET_RESULT(symbolAsNameTable)
                            );
                    String finalResult = resultAll.getString(symbolResult);
                    String stringFinalResult = "0";
                    finalResult = finalResult.replaceAll(",", ".");
                    if (!number.equals("")) {
                        float target = Float.parseFloat(number);
                        float result = Float.parseFloat(finalResult);
                        float stringinalResult = target * result;
                        stringFinalResult = String.format("%.3f",stringinalResult);
                        stringFinalResult = modelResult.getSymbolNative() + ". " + stringFinalResult;
                    }
                    emitter.onNext(stringFinalResult);
                    emitter.onComplete();
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
