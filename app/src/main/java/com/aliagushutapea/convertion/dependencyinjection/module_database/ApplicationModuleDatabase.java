package com.aliagushutapea.convertion.dependencyinjection.module_database;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class ApplicationModuleDatabase {

    Context application;
    Context context;

    public ApplicationModuleDatabase(Context context) {
        this.application = application;
    }

    @Provides
    Context provideContext(){
        return application;
    }

   /* @Provides
    Application provideApplication(){
        return application;
    }*/

    /*@Provides
    @ApplicationHelper
    DatabaseHelper provideDatabaseHelper(){
        return new DatabaseHelper(context);
    }*/
}
