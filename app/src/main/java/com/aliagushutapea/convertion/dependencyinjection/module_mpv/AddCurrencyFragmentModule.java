package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.detail_currency.DetailCurrencyFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 25/01/18.
 */
@Module
public class AddCurrencyFragmentModule {

    DetailCurrencyFragmentContract.View view;

    public AddCurrencyFragmentModule(DetailCurrencyFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    DetailCurrencyFragmentContract.View providesContractView(){
        return view;
    }
}
