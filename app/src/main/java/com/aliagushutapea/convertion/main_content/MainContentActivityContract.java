package com.aliagushutapea.convertion.main_content;

import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 24/01/18.
 */

public interface MainContentActivityContract {

    interface View extends BaseView {

        void attachFragmentAddCurrency();

        void inspecDatabase();

        void detailCurrency(String currencyId);

    }

    interface Presenter extends BasePresenter {
        void saveConfiguration();
    }
}
