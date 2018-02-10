package com.aliagushutapea.convertion.database_helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ali on 25/01/18.
 */
@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    //database version
    public static final int DATABASE_VERSION = 5;
    //database name
    public static final String DATABASE_NAME = "mc.db";

    private static DatabaseHelper instance;

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConfigurationColomn.CREATE_TABLE_CONFIGURATION);
        /*db.execSQL(CurrencyColomn.CREATE_TABLE_CURRENCY);
        db.execSQL(CurrencyTargetColomn.CREATE_TABLE_CURRENCY_TARGET);
        db.execSQL(CurrencyResultColomn.CREATE_TABLE_CURRENCY_RESULT);*/
        db.execSQL(AllCurrencyColomn.CREATE_TABLE_CURRENCY);
        db.execSQL(TargetCurrencyColomn.CREATE_TABLE_TARGET);
        db.execSQL(ResultCurrencyColomn.CREATE_TABLE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConfigurationColomn.TABLE_CONFIGURATION);
        /*db.execSQL("DROP TABLE IF EXISTS " + CurrencyColomn.TABLE_CURRENCY);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyTargetColomn.TABLE_CURRENCY_TARGET);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyResultColomn.TABLE_CURRENCY_RESULT);*/
        db.execSQL("DROP TABLE IF EXISTS " + AllCurrencyColomn.TABLE_ALL_CURRENCY);
        db.execSQL("DROP TABLE IF EXISTS " + TargetCurrencyColomn.TABLE_TARGET);
        db.execSQL("DROP TABLE IF EXISTS " + ResultCurrencyColomn.TABLE_RESULT);
        onCreate(db);
    }

    public static final class ConfigurationColomn implements BaseColumns {
        public static final String TABLE_CONFIGURATION = "configuration";
        public static final String COL_KEY = "key";
        public static final String COL_VALUE = "value";
        public static final String CREATE_TABLE_CONFIGURATION = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CONFIGURATION +
                "(" + COL_KEY + " TEXT, " + COL_VALUE + " TEXT)";
    }

    public class MakeNewTableColomn implements BaseColumns {

    }

    public static final class CurrencyColomn implements BaseColumns {
        public static final String TABLE_CURRENCY = "currency";
        public static final String COL_ID = "id";
        public static final String COL_CURRENCY_ID = "currencyId";
        public static final String COL_CURRENCY_NAME = "currencyName";
        public static final String COL_COUNTRY_NAME = "countryName";
        public static final String COL_CURRENCY_COUNTRY_IMAGE_PATH = "countryImagePath";
        public static final String COL_CURRENCY_IMAGE_PATH = "currencyImagePath";
        public static final String CREATE_TABLE_CURRENCY = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CURRENCY +
                "(" + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_CURRENCY_ID + " VARCHAR(10), "
                + COL_CURRENCY_NAME + " VARCHAR(15), "
                + COL_COUNTRY_NAME + " VARCHAR(25), "
                + COL_CURRENCY_COUNTRY_IMAGE_PATH + " VARCHAR(100), "
                + COL_CURRENCY_IMAGE_PATH + " VARCHAR(100)" + ")";
    }

    public static final class AllCurrencyColomn implements BaseColumns {
        public static final String TABLE_ALL_CURRENCY = "All_Currency";
        public static final String COL_NO = "No";
        public static final String COL_ID = "Id";
        public static final String COL_SYMBOL = "Symbol";
        public static final String COL_NAME = "Name";
        public static final String COL_COUNTRY = "Country";
        public static final String COL_SYMBOL_NATIVE = "Symbol_Native";
        public static final String COL_IMAGE_COUNTRY = "Image_Country";
        public static final String COL_IMAGE_CURRENCY = "Image_Currency";

        public static final String CREATE_TABLE_CURRENCY = "CREATE TABLE IF NOT EXISTS "
                + TABLE_ALL_CURRENCY +
                "("
                + COL_NO +
                " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_ID +
                " VARCHAR(10), "
                + COL_SYMBOL +
                " VARCHAR(20), "
                + COL_NAME +
                " VARCHAR(25), "
                + COL_COUNTRY +
                " VARCHAR(25), "
                + COL_SYMBOL_NATIVE +
                " VARCHAR(10), "
                + COL_IMAGE_COUNTRY +
                " VARCHAR(150), "
                + COL_IMAGE_CURRENCY +
                " VARCHAR(150)"
                + ")";
    }

    public static final class TargetCurrencyColomn implements BaseColumns {
        public static final String TABLE_TARGET = "Target";
        public static final String COL_NO = "No";
        public static final String COL_ID = "Id";
        public static final String COL_SYMBOL = "Symbol";
        public static final String COL_NAME = "Name";
        public static final String COL_COUNTRY = "Country";
        public static final String COL_SYMBOL_NATIVE = "Symbol_Native";
        public static final String COL_IMAGE_COUNTRY = "Image_Country";
        public static final String COL_IMAGE_CURRENCY = "Image_Currency";

        public static final String CREATE_TABLE_TARGET = "CREATE TABLE IF NOT EXISTS "
                + TABLE_TARGET +
                "("
                + COL_NO +
                " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_ID +
                " VARCHAR(10), "
                + COL_SYMBOL +
                " VARCHAR(20), "
                + COL_NAME +
                " VARCHAR(25), "
                + COL_COUNTRY +
                " VARCHAR(25), "
                + COL_SYMBOL_NATIVE +
                " VARCHAR(10), "
                + COL_IMAGE_COUNTRY +
                " VARCHAR(150), "
                + COL_IMAGE_CURRENCY +
                " VARCHAR(150)"
                + ")";
    }

    public static final class ResultCurrencyColomn implements BaseColumns {
        public static final String TABLE_RESULT = "Result";
        public static final String COL_NO = "No";
        public static final String COL_ID = "Id";
        public static final String COL_SYMBOL = "Symbol";
        public static final String COL_NAME = "Name";
        public static final String COL_COUNTRY = "Country";
        public static final String COL_SYMBOL_NATIVE = "Symbol_Native";
        public static final String COL_IMAGE_COUNTRY = "Image_Country";
        public static final String COL_IMAGE_CURRENCY = "Image_Currency";

        public static final String CREATE_TABLE_RESULT = "CREATE TABLE IF NOT EXISTS "
                + TABLE_RESULT +
                "("
                + COL_NO +
                " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_ID +
                " VARCHAR(10), "
                + COL_SYMBOL +
                " VARCHAR(20), "
                + COL_NAME +
                " VARCHAR(25), "
                + COL_COUNTRY +
                " VARCHAR(25), "
                + COL_SYMBOL_NATIVE +
                " VARCHAR(10), "
                + COL_IMAGE_COUNTRY +
                " VARCHAR(150), "
                + COL_IMAGE_CURRENCY +
                " VARCHAR(150)"
                + ")";
    }

    public Boolean isKeyExists(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM sqlite_master WHERE name = ? AND type = ?";
        String[] args = {tableName, "table"};
        Cursor cursor = db.rawQuery(query, args);
        Boolean result = (cursor != null && cursor.getCount() > 0);
        db.close();
        return result;
    }
}
