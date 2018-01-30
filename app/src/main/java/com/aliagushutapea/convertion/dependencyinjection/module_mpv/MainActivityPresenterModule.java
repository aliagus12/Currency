package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.MainActivityContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class MainActivityPresenterModule {

    private MainActivityContract.View view;

    public MainActivityPresenterModule(MainActivityContract.View view) {
        this.view = view;
    }

    @Provides
    MainActivityContract.View providesContractView(){
        return view;
    }
}
