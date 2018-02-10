package com.aliagushutapea.convertion.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.model.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ali on 25/01/18.
 */

public class DatabaseManagerHelper extends DatabaseHelper {

    private static final String TAG = DatabaseManagerHelper.class.getSimpleName();
    @Inject
    DatabaseManager databaseManager;
    private static DatabaseManagerHelper instance;
    static final String COL_NAMECURRENCY_THAT_WANT_TO_COMPARED = "toCurrency";
    static final String COL_VALUE_THAT_COMPARED = "value";

    public DatabaseManagerHelper(Context context) {
        super(context);
        databaseManager = new DatabaseManager(this);
        /*DaggerDatabaseHelperComponent
                .builder()
                .databaseHelperModule(new DatabaseHelperModule(this))
                .applicationComponentDatabase(((MainApplication)context).getApplicationComponentDatabase())
                .build()
                .inject(this);*/
    }

    public static synchronized DatabaseManagerHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManagerHelper(context);
        }
        return instance;
    }

    public void insertCurrencyToDataBase(
            String[] arrayColomn,
            CurrencyModel currencyModel
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(arrayColomn[1], currencyModel.getId());
        contentValues.put(arrayColomn[2], currencyModel.getSymbol());
        contentValues.put(arrayColomn[3], currencyModel.getName());
        contentValues.put(arrayColomn[4], currencyModel.getCountry());
        contentValues.put(arrayColomn[5], currencyModel.getSymbolNative());
        contentValues.put(arrayColomn[6], currencyModel.getImageCountry());
        contentValues.put(arrayColomn[7], currencyModel.getImageCurrency());
        CurrencyModel model = getCurrencyModelByKey(
                arrayColomn,
                currencyModel.getId()
        );
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        if (model.getId() != null && !model.getId().equals("")) {
            String where = arrayColomn[1] + " = ?";
            String[] args = new String[]{currencyModel.getId()};
            sqLiteDatabase.update(arrayColomn[0], contentValues, where, args);
        } else {
            sqLiteDatabase.insert(arrayColomn[0], null, contentValues);
        }
    }

    public void deleteCurrencyModelByCurrencyIdFromDatabaseHelper(String currencyId) {
        String where = CurrencyColomn.COL_CURRENCY_ID + " = ?";
        String[] args = new String[]{currencyId};
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        sqLiteDatabase.delete(CurrencyColomn.TABLE_CURRENCY, where, args);
    }

    public String fetchRowNameByCurrencyIdFromDatabaseHelper(String key) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        Cursor cursor = sqLiteDatabase.query(
                CurrencyColomn.TABLE_CURRENCY,
                null,
                CurrencyColomn.COL_CURRENCY_ID + "='" + key + "'",
                null,
                null,
                null,
                null
        );
        String value = "";
        Integer count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_NAME));
        }
        cursor.close();
        return value;
    }

    public String getFieldRow(String tableName, String colomn, String key) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        Cursor cursor = sqLiteDatabase.query(
                tableName,
                null,
                colomn + "='" + key + "'",
                null,
                null,
                null,
                null
        );
        String value = "";
        Integer count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndex(colomn));
        }
        cursor.close();
        return value;
    }

    public Boolean isExists(String[] arrayColomn, String key) {
        CurrencyModel model = getCurrencyModelByKey(
                arrayColomn,
                key
        );
        String id = model.getSymbol();
        Boolean result = false;
        if (!id.equals("")) {
            result = true;
        }
        return result;
    }

    public CurrencyModel getCurrencyModelByKey(
            String[] arrayColomn,
            String key
    ) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + arrayColomn[0] + " WHERE "
                + arrayColomn[1] + " = '" + key + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        CurrencyModel currencyModel = new CurrencyModel();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            currencyModel.setId(cursor.getString(cursor.getColumnIndex(arrayColomn[1])));
            currencyModel.setSymbol(cursor.getString(cursor.getColumnIndex(arrayColomn[2])));
            currencyModel.setName(cursor.getString(cursor.getColumnIndex(arrayColomn[3])));
            currencyModel.setCountry(cursor.getString(cursor.getColumnIndex(arrayColomn[4])));
            currencyModel.setSymbolNative(cursor.getString(cursor.getColumnIndex(arrayColomn[5])));
            currencyModel.setImageCountry(cursor.getString(cursor.getColumnIndex(arrayColomn[6])));
            currencyModel.setImageCurrency(cursor.getString(cursor.getColumnIndex(arrayColomn[7])));
        } else {
            currencyModel.setId("");
            currencyModel.setSymbol("");
            currencyModel.setName("");
            currencyModel.setCountry("");
            currencyModel.setSymbolNative("");
            currencyModel.setImageCountry("");
            currencyModel.setImageCurrency("");
        }
        cursor.close();
        return currencyModel;
    }

    public Observable<List<CurrencyModel>> getObservableAllCurrencyFromDatabase(String[] arrayColomn) {
        return makeObservable(arrayColomn)
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<CurrencyModel>> makeObservable(final String[] arrayColomn) {
        return Observable.create(new ObservableOnSubscribe<List<CurrencyModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CurrencyModel>> e) throws Exception {
                try {
                    e.onNext(getAllCurrencyFromDatabase(arrayColomn));
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }

    public List<CurrencyModel> getAllCurrencyFromDatabase(String[] arrayColomn) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + arrayColomn[0];
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        List<CurrencyModel> currencyModelList = new ArrayList<>();
        List<String> listIdCurrency = getListSymbolCurrency(arrayColomn);

        if (cursor.getCount() > 0) {
            for (String key : listIdCurrency) {
                CurrencyModel currencyModel = getCurrencyModelByKey(
                        arrayColomn,
                        key
                );
                currencyModelList.add(currencyModel);
            }
        }
        return currencyModelList;
    }

    public JSONObject getAllCurrencyValueFromDatabase(String[] arrayStrings) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + arrayStrings[0];
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        JSONObject jsonObjectValue = new JSONObject();
        List<String> listSymbolValue = getListSymbolCurrency(arrayStrings);
        if (cursor.getCount() > 0) {
            for (String symbol : listSymbolValue) {
                ResultModel resultModel = getResultModelByKey(
                        arrayStrings,
                        symbol
                );
                try {
                    jsonObjectValue.put(resultModel.getSymbol(), resultModel.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObjectValue;
    }

    private List<String> getListSymbolCurrency(String[] arrayColomn) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + arrayColomn[0];
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        List<String> listIdCurrency = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    listIdCurrency.add(cursor.getString(cursor.getColumnIndex(arrayColomn[1])));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return listIdCurrency;
    }

    private ResultModel getResultModelByKey(
            String[] arrayStrings,
            String symbol
    ) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + arrayStrings[0] + " WHERE "
                + arrayStrings[1] + " = '" + symbol + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        ResultModel resultModel = new ResultModel();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            resultModel.setSymbol(cursor.getString(cursor.getColumnIndex(arrayStrings[1])));
            resultModel.setValue(cursor.getString(cursor.getColumnIndex(arrayStrings[2])));
        } else {
            resultModel.setSymbol("0");
            resultModel.setValue("0");
        }
        cursor.close();
        return resultModel;
    }

    private long getCountAllRow() {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, CurrencyColomn.CREATE_TABLE_CURRENCY);
        sqLiteDatabase.close();
        return count;
    }

    public boolean isTableKeyExists(String nameTable) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",
                null
        );
        int i = 0;
        ArrayList<String> rowListNameTable = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                i++;
                rowListNameTable.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        if (rowListNameTable.contains(nameTable)) {
            return true;
        }
        return false;
    }

    public void addNewTableToDatabase(String nameTable) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String createNewTable = "CREATE TABLE IF NOT EXISTS "
                + nameTable +
                "("
                + COL_NAMECURRENCY_THAT_WANT_TO_COMPARED +
                " VARCHAR(100), "
                + COL_VALUE_THAT_COMPARED +
                " VARCHAR(100)"
                + ")";
        sqLiteDatabase.execSQL(createNewTable);
    }

    public void saveCurrencyToTargetOrResult(
            String[] arrayColomn,
            CurrencyModel currencyModel
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(arrayColomn[1], currencyModel.getId());
        contentValues.put(arrayColomn[2], currencyModel.getSymbol());
        contentValues.put(arrayColomn[3], currencyModel.getName());
        contentValues.put(arrayColomn[4], currencyModel.getCountry());
        contentValues.put(arrayColomn[5], currencyModel.getSymbolNative());
        contentValues.put(arrayColomn[6], currencyModel.getImageCountry());
        contentValues.put(arrayColomn[7], currencyModel.getImageCurrency());
        boolean isHaveField = getListSymbolCurrency(arrayColomn).size() > 0 ? true : false;
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        if (isHaveField) {
            String oldCurrencyId = getListSymbolCurrency(arrayColomn).get(0);
            String where = arrayColomn[1] + " = ?";
            String[] args = new String[]{oldCurrencyId};
            sqLiteDatabase.update(arrayColomn[0], contentValues, where, args);
        } else {
            sqLiteDatabase.insert(arrayColomn[0], null, contentValues);
        }
    }

    public CurrencyModel getCurrencyModelWithoutKey(String[] arrayColomn) {
        List<String> listCurrencyId = getListSymbolCurrency(arrayColomn);
        if (listCurrencyId != null && listCurrencyId.size() != 0) {
            String key = getListSymbolCurrency(arrayColomn).get(0);
            CurrencyModel currencyModel = getCurrencyModelByKey(
                    arrayColomn,
                    key
            );
            return currencyModel;
        }
        return new CurrencyModel();
    }

    public String saveConfiguration(
            String[] arrayColomn,
            CurrencyModel currencyModel
    ) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        ContentValues contentValues = new ContentValues();
        contentValues.put(arrayColomn[1], currencyModel.getKeyConfiguration());
        contentValues.put(arrayColomn[2], currencyModel.getValueConfiguration());
        boolean isHaveField = getListSymbolCurrency(arrayColomn).size() > 0 ? true : false;
        if (isHaveField) {
            String oldCurrencyId = getListSymbolCurrency(arrayColomn).get(0);
            String where = arrayColomn[1] + " = ?";
            String[] args = new String[]{oldCurrencyId};
            sqLiteDatabase.update(arrayColomn[0], contentValues, where, args);
        } else {
            sqLiteDatabase.insert(ConfigurationColomn.TABLE_CONFIGURATION, null, contentValues);
        }
        return currencyModel.getValueConfiguration();
    }

    public String getConfiguration(
            String[] arrayColomn,
            String key
    ) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        Cursor cursor = sqLiteDatabase.query(
                arrayColomn[0],
                null,
                arrayColomn[1] + "='" + key + "'",
                null,
                null,
                null,
                null
        );
        String value = "";
        Integer count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndex(arrayColomn[2]));
        }
        cursor.close();
        return value;
    }

    public void bulkInsertDataCurrencyToDatabase(List<JSONObject> listAllDataCurrency) {
        SQLiteDatabase db = databaseManager.openDatabase(TAG);
        String query = "INSERT OR REPLACE INTO "
                + AllCurrencyColomn.TABLE_ALL_CURRENCY +
                " ( "
                + AllCurrencyColomn.COL_ID +
                ", "
                + AllCurrencyColomn.COL_SYMBOL +
                ", "
                + AllCurrencyColomn.COL_NAME +
                ", "
                + AllCurrencyColomn.COL_COUNTRY +
                ", "
                + AllCurrencyColomn.COL_SYMBOL_NATIVE +
                ", "
                + AllCurrencyColomn.COL_IMAGE_COUNTRY +
                ", "
                + AllCurrencyColomn.COL_IMAGE_CURRENCY +
                ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try {
            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(query);
            for (JSONObject jsonObjectDataCurrency : listAllDataCurrency) {
                stmt.clearBindings();
                stmt.bindString(1, jsonObjectDataCurrency.getString("id"));
                stmt.bindString(2, jsonObjectDataCurrency.getString("symbol"));
                stmt.bindString(3, jsonObjectDataCurrency.getString("name"));
                stmt.bindString(4, jsonObjectDataCurrency.getString("country"));
                stmt.bindString(5, jsonObjectDataCurrency.getString("symbol_native"));
                stmt.bindString(6, jsonObjectDataCurrency.getString("flag_uri"));
                stmt.bindString(7, jsonObjectDataCurrency.getString("currency_uri"));
                stmt.execute();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void bulkInsertDataValueCurrencyToDatabase(
            String nameTable,
            JSONArray jsonArray,
            List<String> listKeys
    ) throws JSONException {
        List<JSONObject> listAllDataCurrency = new ArrayList<>();
        for (int a = 0; a < jsonArray.length(); a++) {
            listAllDataCurrency.add(jsonArray.getJSONObject(a));
        }
        SQLiteDatabase db = databaseManager.openDatabase(TAG);
        String query = "INSERT OR REPLACE INTO "
                + nameTable +
                " ( "
                + COL_NAMECURRENCY_THAT_WANT_TO_COMPARED +
                ", "
                + COL_VALUE_THAT_COMPARED +
                ") "
                + "VALUES (?, ?) ";
        try {
            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(query);

            for (int a = 0; a < listKeys.size(); a++) {
                JSONObject jsonObject = listAllDataCurrency.get(a);
                String value = jsonObject.getString(listKeys.get(a));
                stmt.clearBindings();
                stmt.bindString(1, listKeys.get(a));
                stmt.bindString(2, value);
                stmt.execute();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
