package com.aliagushutapea.convertion.dependencyinjection.component_database;

import android.content.Context;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.dependencyinjection.module_database.ApplicationModuleDatabase;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */
@Singleton
@Component(modules = ApplicationModuleDatabase.class)
public interface ApplicationComponentDatabase {

    void inject(MainApplication mainApplication);

    //Application getApplication();

    Context context();

}
