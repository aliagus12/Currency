package com.aliagushutapea.convertion.database_helper;

import android.database.sqlite.SQLiteDatabase;

import com.aliagushutapea.convertion.dependencyinjection.ApplicationHelper;
import com.aliagushutapea.convertion.dependencyinjection.PerDatabaseHelper;

import javax.inject.Inject;

/**
 * Created by ali on 25/01/18.
 */
@PerDatabaseHelper
public class DatabaseManager {

    private static final String TAG = DatabaseManager.class.getSimpleName();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Inject
    public DatabaseManager(@ApplicationHelper DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public SQLiteDatabase openDatabase(String tag){
        if (sqLiteDatabase == null){
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        } else if (!sqLiteDatabase.isOpen()){
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }
}
