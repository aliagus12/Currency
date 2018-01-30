package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.add_currency.AddCurrencyFragment;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.AddCurrencyFragmentModule;

import dagger.Component;

/**
 * Created by ali on 25/01/18.
 */
@Component(dependencies = ApplicationComponentMVP.class,
        modules = AddCurrencyFragmentModule.class)
public interface AddCurrencyFragmentComponent {

    void inject(AddCurrencyFragment addCurrencyFragment);
}
