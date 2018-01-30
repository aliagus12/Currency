package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MoneyExchangeFragmentPresenterModule;
import com.aliagushutapea.convertion.money_exchange.MoneyExchangeFragment;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */

@Component(dependencies = ApplicationComponentMVP.class,
        modules = MoneyExchangeFragmentPresenterModule.class)
public interface MoneyExchangeComponent {

    void inject(MoneyExchangeFragment moneyExchangeFragment);
}
