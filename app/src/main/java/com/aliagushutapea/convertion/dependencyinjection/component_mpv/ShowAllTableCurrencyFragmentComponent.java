package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.dependencyinjection.module_mpv.ShowAllTableCurrencyFragmentModule;
import com.aliagushutapea.convertion.show_all_table.ShowAllTableCurrencyFragment;

import dagger.Component;

/**
 * Created by ali on 27/01/18.
 */
@Component(dependencies = ApplicationComponentMVP.class,
modules = ShowAllTableCurrencyFragmentModule.class
)
public interface ShowAllTableCurrencyFragmentComponent {
    void inject(ShowAllTableCurrencyFragment showAllTableCurrencyFragment);
}
