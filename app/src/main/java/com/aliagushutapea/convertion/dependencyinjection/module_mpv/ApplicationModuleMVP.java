package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import android.content.Context;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class ApplicationModuleMVP {

    Context context;
    CurrencyModel currencyModel;
    DatabaseManagerHelper instance;

    public ApplicationModuleMVP(Context context) {
        this.context = context;
        currencyModel = new CurrencyModel();
        instance = DatabaseManagerHelper.getInstance(context);
    }

    @Provides
    Context provideContext(){
        return context;
    }

    @Provides
    CurrencyModel provideCurrencyModel(){
        return currencyModel;
    }

    @Provides
    DatabaseManagerHelper provideDatabaseManagerHelper(){
        return instance;
    }

}
