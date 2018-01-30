package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.add_table_currency.AddTableCurrencyFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 27/01/18.
 */
@Module
public class AddTableCurrencyFragmentModule {
    private AddTableCurrencyFragmentContract.View view;

    public AddTableCurrencyFragmentModule(AddTableCurrencyFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    AddTableCurrencyFragmentContract.View provideAddTableCurrencyFragmentContractView(){
        return view;
    }
}
