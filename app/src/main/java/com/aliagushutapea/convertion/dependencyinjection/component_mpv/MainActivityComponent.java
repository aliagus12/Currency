package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.MainActivity;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MainActivityPresenterModule;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */
@Component(dependencies = ApplicationComponentMVP.class,
        modules = MainActivityPresenterModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}
