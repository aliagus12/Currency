package com.aliagushutapea.convertion.add_currency;

import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 25/01/18.
 */

public interface AddCurrencyFragmentContract {

    interface View extends BaseView {

        void dismissDialog();
    }

    interface Presenter extends BasePresenter {

        void saveDataCurrencyToDataBase(
                String idCurrency,
                String nameCurrency,
                String countryCurrency,
                String valueCurrency,
                String imagePathCurrency
        );
    }
}
