package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.currency_exchange.CurrencyExchangeFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class MoneyExchangeFragmentPresenterModule {

    CurrencyExchangeFragmentContract.View view;

    public MoneyExchangeFragmentPresenterModule(CurrencyExchangeFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    CurrencyExchangeFragmentContract.View provideMoneyContractView(){
        return view;
    }
}
