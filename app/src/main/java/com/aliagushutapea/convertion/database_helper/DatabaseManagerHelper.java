package com.aliagushutapea.convertion.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.aliagushutapea.convertion.model.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    private String[] columnKeyValue = new String[]{
            KeyValueColomn.TABLE_KV,
            KeyValueColomn.COL_KEY,
            KeyValueColomn.COL_VALUE
    };

    private String[] columnCurrency = new String[]{
            CurrencyColomn.TABLE_CURRENCY,
            CurrencyColomn.COL_ID,
            CurrencyColomn.COL_CURRENCY_ID,
            CurrencyColomn.COL_CURRENCY_NAME,
            CurrencyColomn.COL_COUNTRY_NAME,
            CurrencyColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH,
            CurrencyColomn.COL_CURRENCY_IMAGE_PATH
    };

    public static synchronized DatabaseManagerHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManagerHelper(context);
        }
        return instance;
    }

    public void addCurrency(
            String currencyId,
            String currencyName,
            String currencyCountry,
            String currencyValue,
            String currencyImagePath
    ) {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setCurrencyId(currencyId);
        currencyModel.setCurrencyName(currencyName);
        currencyModel.setCurrencyCountryPath(currencyCountry);
        currencyModel.setCountryName(currencyValue);
        currencyModel.setCurrencyImagePath(currencyImagePath);
        addCurrency(currencyModel);
    }

    public void addCurrency(CurrencyModel currencyModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyColomn.COL_CURRENCY_ID, currencyModel.getCurrencyId());
        contentValues.put(CurrencyColomn.COL_CURRENCY_NAME, currencyModel.getCurrencyName());
        contentValues.put(CurrencyColomn.COL_COUNTRY_NAME, currencyModel.getNameCountry());
        contentValues.put(CurrencyColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH, currencyModel.getCurrencyCountryPath());
        contentValues.put(CurrencyColomn.COL_CURRENCY_IMAGE_PATH, currencyModel.getCurrencyImagePath());
        CurrencyModel exist = getCurrencyModelByCurrencyIdFromDatabaseHelper(
                Integer.parseInt(currencyModel.getCurrencyId()),
                CurrencyColomn.COL_CURRENCY_ID,
                CurrencyColomn.TABLE_CURRENCY
        );
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        if (exist.getCurrencyId() != null && Integer.parseInt(exist.getCurrencyId()) > 0) {
            String where = CurrencyColomn.COL_CURRENCY_ID + " = ?";
            String[] args = new String[]{currencyModel.getCurrencyId()};
            sqLiteDatabase.update(CurrencyColomn.TABLE_CURRENCY, contentValues, where, args);
        } else {
            sqLiteDatabase.insert(CurrencyColomn.TABLE_CURRENCY, null, contentValues);
        }
    }

    public CurrencyModel getCurrencyModelByCurrencyIdFromDatabaseHelper(
            int id,
            String colCurrencyId,
            String tableNameCurrency
    ) {
        String where = colCurrencyId + " = ?";
        String[] args = new String[]{String.valueOf(id)};
        return fetchCurrencyModelByCurrencyIdFromDatabaseHelper(
                where,
                args,
                null,
                null,
                null,
                null,
                tableNameCurrency
        );
    }

    private CurrencyModel fetchCurrencyModelByCurrencyIdFromDatabaseHelper(
            String where,
            String[] args,
            String group,
            String having,
            String order,
            String limit,
            String tableNameCurrency
    ) {
        String[] columnTbCurrency = new String[]{
                CurrencyColomn.COL_CURRENCY_ID,
                CurrencyColomn.COL_CURRENCY_NAME,
                CurrencyColomn.COL_COUNTRY_NAME,
                CurrencyColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH,
                CurrencyColomn.COL_CURRENCY_IMAGE_PATH
        };
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        Cursor cursor = sqLiteDatabase.query(
                tableNameCurrency,
                columnTbCurrency,
                where,
                args,
                group,
                having,
                order,
                limit
        );
        CurrencyModel currencyModel = new CurrencyModel();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            currencyModel.setCurrencyId(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_ID)));
            currencyModel.setCurrencyName(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_NAME)));
            currencyModel.setCountryName(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_COUNTRY_NAME)));
            currencyModel.setCurrencyCountryPath(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH)));
            currencyModel.setCurrencyImagePath(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_IMAGE_PATH)));
        }
        cursor.close();
        return currencyModel;
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

    public Boolean isCurrencyIdExists(String idCurrency, String nameTable) {
        CurrencyModel currencyModel = fetchCurrencyModelByCurrencyIdFromDatabaseHelper(
                idCurrency,
                nameTable
        );
        String id = currencyModel.getCurrencyId();
        Boolean result = false;
        if (!id.equals("")) {
            result = true;
        }
        return result;
    }

    public CurrencyModel fetchCurrencyModelByCurrencyIdFromDatabaseHelper(
            String idCurrency,
            String nameTable
    ) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + nameTable + " WHERE "
                + CurrencyColomn.COL_CURRENCY_ID + " = '" + idCurrency + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        CurrencyModel currencyModel = new CurrencyModel();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            currencyModel.setCurrencyId(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_ID)));
            currencyModel.setCurrencyName(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_NAME)));
            currencyModel.setCountryName(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_COUNTRY_NAME)));
            currencyModel.setCurrencyCountryPath(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH)));
            currencyModel.setCurrencyImagePath(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_IMAGE_PATH)));
        } else {
            currencyModel.setCurrencyId("");
            currencyModel.setCurrencyName("");
            currencyModel.setCurrencyCountryPath("");
            currencyModel.setCountryName("");
            currencyModel.setCurrencyImagePath("");
        }
        cursor.close();
        return currencyModel;
    }

    public List<CurrencyModel> fetchAllCurrencyModelFromDatabaseManagerHelper() {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + CurrencyColomn.TABLE_CURRENCY;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        List<CurrencyModel> currencyModelList = new ArrayList<>();
        List<String> listIdCurrency = getListIdCurrency(CurrencyColomn.TABLE_CURRENCY);

        if (cursor.getCount() > 0) {
            for (String id : listIdCurrency) {
                CurrencyModel currencyModel = fetchCurrencyModelByCurrencyIdFromDatabaseHelper(
                        id,
                        CurrencyColomn.TABLE_CURRENCY
                );
                currencyModelList.add(currencyModel);
            }
        }
        return currencyModelList;
    }

    private List<String> getListIdCurrency(String nameTable) {
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        String selectQuery = "SELECT * FROM " + nameTable;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        List<String> listIdCurrency = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    listIdCurrency.add(cursor.getString(cursor.getColumnIndex(CurrencyColomn.COL_CURRENCY_ID)));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return listIdCurrency;
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
                "(" + COL_NAMECURRENCY_THAT_WANT_TO_COMPARED + " TEXT, " + COL_VALUE_THAT_COMPARED + " TEXT)";
        sqLiteDatabase.execSQL(createNewTable);
    }

    public void saveCurrencyToTable(
            CurrencyModel currencyModel,
            String tableName,
            String colCurrencyId
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyTargetColomn.COL_CURRENCY_ID, currencyModel.getCurrencyId());
        contentValues.put(CurrencyTargetColomn.COL_CURRENCY_NAME, currencyModel.getCurrencyName());
        contentValues.put(CurrencyTargetColomn.COL_COUNTRY_NAME, currencyModel.getNameCountry());
        contentValues.put(CurrencyTargetColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH, currencyModel.getCurrencyCountryPath());
        contentValues.put(CurrencyTargetColomn.COL_CURRENCY_IMAGE_PATH, currencyModel.getCurrencyImagePath());
        boolean isHaveField = getListIdCurrency(tableName).size() != 0 ? true : false;
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        if (isHaveField) {
            String oldCurrencyId = getListIdCurrency(tableName).get(0);
            String where = colCurrencyId + " = ?";
            String[] args = new String[]{oldCurrencyId};
            sqLiteDatabase.update(tableName, contentValues, where, args);
        } else {
            sqLiteDatabase.insert(tableName, null, contentValues);
        }
    }

    public void saveCurrencyToTableResult(CurrencyModel currencyModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyResultColomn.COL_CURRENCY_ID, currencyModel.getCurrencyId());
        contentValues.put(CurrencyResultColomn.COL_CURRENCY_NAME, currencyModel.getCurrencyName());
        contentValues.put(CurrencyResultColomn.COL_COUNTRY_NAME, currencyModel.getNameCountry());
        contentValues.put(CurrencyResultColomn.COL_CURRENCY_COUNTRY_IMAGE_PATH, currencyModel.getCurrencyCountryPath());
        contentValues.put(CurrencyResultColomn.COL_CURRENCY_IMAGE_PATH, currencyModel.getCurrencyImagePath());
        boolean isHaveField = getListIdCurrency(CurrencyResultColomn.TABLE_CURRENCY_RESULT).size() != 0 ? true : false;
        String oldCurrencyId = getListIdCurrency(CurrencyResultColomn.TABLE_CURRENCY_RESULT).get(0);
        SQLiteDatabase sqLiteDatabase = databaseManager.openDatabase(TAG);
        if (isHaveField) {
            String where = CurrencyResultColomn.COL_CURRENCY_ID + " = ?";
            String[] args = new String[]{oldCurrencyId};
            sqLiteDatabase.update(CurrencyResultColomn.TABLE_CURRENCY_RESULT, contentValues, where, args);
        } else {
            sqLiteDatabase.insert(CurrencyResultColomn.TABLE_CURRENCY_RESULT, null, contentValues);
        }
    }

    public CurrencyModel fetchCurrencyModelFromDatabaseHelper(
            String tableName,
            String colCurrencyId
    ) {
        List<String> listCurrencyId = getListIdCurrency(tableName);
        if (listCurrencyId != null && listCurrencyId.size() != 0){
            String currencyId = getListIdCurrency(tableName).get(0) ;
            CurrencyModel currencyModel = getCurrencyModelByCurrencyIdFromDatabaseHelper(
                    Integer.parseInt(currencyId),
                    colCurrencyId,
                    tableName
            );
            return currencyModel;
        }
        return new CurrencyModel();
    }
}
