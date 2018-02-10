package com.aliagushutapea.convertion;

import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 07/01/18.
 */

public interface MainActivityContract {

    interface View extends BaseView {
        void toMainContentAndDissmisProgressDialog();
    }

    interface Presenter extends BasePresenter {
        void insertDataToDatabase();

        void insertDataCurrencyToDatabase() throws Exception;
    }
}
