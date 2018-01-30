package com.aliagushutapea.convertion.dependencyinjection.module_database;

import com.aliagushutapea.convertion.database_helper.DatabaseHelper;
import com.aliagushutapea.convertion.dependencyinjection.ApplicationHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 26/01/18.
 */
@Module
public class DatabaseHelperModule {

    DatabaseHelper mDatabaseHelper;

    public DatabaseHelperModule(DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    @Provides
    @ApplicationHelper
    DatabaseHelper provideDatabaseHelper(){
        return mDatabaseHelper;
    }

}
