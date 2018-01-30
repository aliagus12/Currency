package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.show_all_table.ShowAllTableCurrencyFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 27/01/18.
 */
@Module
public class ShowAllTableCurrencyFragmentModule {
    private ShowAllTableCurrencyFragmentContract.View view;

    public ShowAllTableCurrencyFragmentModule(ShowAllTableCurrencyFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    ShowAllTableCurrencyFragmentContract.View provideShowAllTableCurrencyFragmentContractView(){
        return view;
    }
}
