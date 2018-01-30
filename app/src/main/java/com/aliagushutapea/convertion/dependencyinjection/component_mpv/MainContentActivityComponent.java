package com.aliagushutapea.convertion.dependencyinjection.component_mpv;

import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MainContentActivityPresenterModule;
import com.aliagushutapea.convertion.main_content.MainContentActivity;

import dagger.Component;

/**
 * Created by ali on 24/01/18.
 */
@Component(dependencies = ApplicationComponentMVP.class,
        modules = MainContentActivityPresenterModule.class)
public interface MainContentActivityComponent {

    void inject(MainContentActivity mainContentActivity);
}
