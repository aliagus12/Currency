package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.money_exchange.MoneyExchangeFragmentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class MoneyExchangeFragmentPresenterModule {

    MoneyExchangeFragmentContract.View view;

    public MoneyExchangeFragmentPresenterModule(MoneyExchangeFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    MoneyExchangeFragmentContract.View provideMoneyContractView(){
        return view;
    }
}
