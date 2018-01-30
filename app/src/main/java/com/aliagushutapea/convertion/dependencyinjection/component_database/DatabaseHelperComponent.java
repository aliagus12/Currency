package com.aliagushutapea.convertion.dependencyinjection.component_database;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.dependencyinjection.PerDatabaseHelper;
import com.aliagushutapea.convertion.dependencyinjection.module_database.DatabaseHelperModule;

import dagger.Component;

/**
 * Created by ali on 26/01/18.
 */
@PerDatabaseHelper
@Component(dependencies = ApplicationComponentDatabase.class,
        modules = DatabaseHelperModule.class
)
public interface DatabaseHelperComponent {

    void inject(DatabaseManagerHelper databaseManagerHelper);
}
