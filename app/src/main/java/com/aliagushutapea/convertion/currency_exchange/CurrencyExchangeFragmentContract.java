package com.aliagushutapea.convertion.currency_exchange;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 07/01/18.
 */

public interface CurrencyExchangeFragmentContract {

    interface View extends BaseView {

        void showAllCurrency(android.view.View view);

        void loadDataToView(CurrencyModel target, CurrencyModel result);

        void setTextViewResult(String finalResult);
    }

    interface Presenter extends BasePresenter {

        void loadData();

        void onNumberChanges(String number);
    }
}
