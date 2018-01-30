package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import android.content.Context;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.ApplicationModuleMVP;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */
@Component(modules = ApplicationModuleMVP.class)
public interface ApplicationComponentMVP {

    Context context();

    CurrencyModel currencyModel();

    DatabaseManagerHelper databaseManagerHelper();

}
