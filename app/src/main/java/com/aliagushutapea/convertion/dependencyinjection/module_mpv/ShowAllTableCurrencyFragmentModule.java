package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.show_all_table.ShowAllListCurrencyFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 27/01/18.
 */
@Module
public class ShowAllTableCurrencyFragmentModule {
    private ShowAllListCurrencyFragmentContract.View view;

    public ShowAllTableCurrencyFragmentModule(ShowAllListCurrencyFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    ShowAllListCurrencyFragmentContract.View provideShowAllTableCurrencyFragmentContractView(){
        return view;
    }
}
