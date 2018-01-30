package com.aliagushutapea.convertion.money_exchange;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 07/01/18.
 */

public interface MoneyExchangeFragmentContract {

    interface View extends BaseView {

        void showAllCurrency(android.view.View view);

        void loadDataToView(CurrencyModel target, CurrencyModel result);
    }

    interface Presenter extends BasePresenter {

        void loadData();
    }
}
