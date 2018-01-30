package com.aliagushutapea.convertion.dependencyinjection.module_mpv;

import com.aliagushutapea.convertion.main_content.MainContentActivityContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ali on 24/01/18.
 */
@Module
public class MainContentActivityPresenterModule {

    MainContentActivityContract.View view;

    public MainContentActivityPresenterModule(MainContentActivityContract.View view) {
        this.view = view;
    }

    @Provides
    MainContentActivityContract.View provideMainActivityContractView(){
        return view;
    }
}
