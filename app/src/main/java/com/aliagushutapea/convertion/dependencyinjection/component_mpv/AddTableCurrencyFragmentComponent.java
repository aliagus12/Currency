package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.add_table_currency.AddTableCurrencyFragment;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.AddTableCurrencyFragmentModule;

import dagger.Component;

/**
 * Created by ali on 27/01/18.
 */
@Component(dependencies = ApplicationComponentMVP.class,
        modules = AddTableCurrencyFragmentModule.class
)
public interface AddTableCurrencyFragmentComponent {

    void inject(AddTableCurrencyFragment addTableCurrencyFragment);

}
