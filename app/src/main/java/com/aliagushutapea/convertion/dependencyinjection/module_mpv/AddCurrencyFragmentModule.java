package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.add_currency.AddCurrencyFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 25/01/18.
 */
@Module
public class AddCurrencyFragmentModule {

    AddCurrencyFragmentContract.View view;

    public AddCurrencyFragmentModule(AddCurrencyFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    AddCurrencyFragmentContract.View providesContractView(){
        return view;
    }
}
