package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MoneyExchangeFragmentPresenterModule;
import com.aliagushutapea.convertion.currency_exchange.CurrencyExchangeFragment;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */

@Component(dependencies = ApplicationComponentMVP.class,
        modules = MoneyExchangeFragmentPresenterModule.class)
public interface MoneyExchangeComponent {

    void inject(CurrencyExchangeFragment currencyExchangeFragment);
}
