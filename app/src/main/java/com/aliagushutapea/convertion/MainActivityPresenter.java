package com.aliagushutapea.convertion;

import javax.inject.Inject;

/**
 * Created by ali on 06/01/18.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {


    private MainActivityContract.View view;

    @Inject
    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

}
