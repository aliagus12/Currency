package com.aliagushutapea.convertion.money_exchange;

import android.support.v4.app.Fragment;

import com.aliagushutapea.convertion.MainActivityContract;

import static com.aliagushutapea.convertion.utils.CheckingPrecondition.checkNotNull;

/**
 * Created by ali on 07/01/18.
 */

public class TaskFragment extends Fragment {

    private MainActivityContract.Presenter mPresenter;

    public TaskFragment() {
    }

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    //@Override
    public void setPresenter(MainActivityContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter, "Presenter Null");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
