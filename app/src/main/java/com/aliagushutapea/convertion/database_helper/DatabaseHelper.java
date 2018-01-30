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
    public static final int DATABASE_VERSION = 4;
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
        db.execSQL(KeyValueColomn.CREATE_TABLE_KV);
        db.execSQL(CurrencyColomn.CREATE_TABLE_CURRENCY);
        db.execSQL(CurrencyTargetColomn.CREATE_TABLE_CURRENCY_TARGET);
        db.execSQL(CurrencyResultColomn.CREATE_TABLE_CURRENCY_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KeyValueColomn.TABLE_KV);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyColomn.TABLE_CURRENCY);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyTargetColomn.TABLE_CURRENCY_TARGET);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyResultColomn.TABLE_CURRENCY_RESULT);
        onCreate(db);
    }

    public static final class KeyValueColomn implements BaseColumns {
        public static final String TABLE_KV = "kv";
        public static final String COL_KEY = "key";
        public static final String COL_VALUE = "value";
        public static final String CREATE_TABLE_KV = "CREATE TABLE IF NOT EXISTS "
                + TABLE_KV +
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

    public static final class CurrencyTargetColomn implements BaseColumns {
        public static final String TABLE_CURRENCY_TARGET = "currencyTarget";
        public static final String COL_ID = "idTarget";
        public static final String COL_CURRENCY_ID = "currencyId";
        public static final String COL_CURRENCY_NAME = "currencyName";
        public static final String COL_COUNTRY_NAME = "countryName";
        public static final String COL_CURRENCY_COUNTRY_IMAGE_PATH = "countryImagePath";
        public static final String COL_CURRENCY_IMAGE_PATH = "currencyImagePath";

        public static final String CREATE_TABLE_CURRENCY_TARGET = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CURRENCY_TARGET +
                "(" + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_CURRENCY_ID + " VARCHAR(10), "
                + COL_CURRENCY_NAME + " VARCHAR(15), "
                + COL_COUNTRY_NAME + " VARCHAR(25), "
                + COL_CURRENCY_COUNTRY_IMAGE_PATH + " VARCHAR(100), "
                + COL_CURRENCY_IMAGE_PATH + " VARCHAR(100)" + ")";
    }

    public static final class CurrencyResultColomn implements BaseColumns {
        public static final String TABLE_CURRENCY_RESULT = "currencyResult";
        public static final String COL_ID = "idResult";
        public static final String COL_CURRENCY_ID = "currencyId";
        public static final String COL_CURRENCY_NAME = "currencyName";
        public static final String COL_COUNTRY_NAME = "countryName";
        public static final String COL_CURRENCY_COUNTRY_IMAGE_PATH = "countryImagePath";
        public static final String COL_CURRENCY_IMAGE_PATH = "currencyImagePath";

        public static final String CREATE_TABLE_CURRENCY_RESULT = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CURRENCY_RESULT +
                "(" + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COL_CURRENCY_ID + " VARCHAR(10), "
                + COL_CURRENCY_NAME + " VARCHAR(15), "
                + COL_COUNTRY_NAME + " VARCHAR(25), "
                + COL_CURRENCY_COUNTRY_IMAGE_PATH + " VARCHAR(100), "
                + COL_CURRENCY_IMAGE_PATH + " VARCHAR(100)" + ")";
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
