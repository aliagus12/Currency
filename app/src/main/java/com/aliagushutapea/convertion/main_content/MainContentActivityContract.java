package com.aliagushutapea.convertion.main_content;

import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;
import com.aliagushutapea.convertion.model.CurrencyModel;

/**
 * Created by ali on 24/01/18.
 */

public interface MainContentActivityContract {

    interface View extends BaseView {

        void attachFragmentAddCurrency();

        void inspecDatabase();

        void detailCurrency(CurrencyModel currencyId);

        void attachContentToHeader(
                String name,
                String symbol,
                String imageCurrency,
                String imageCountry
        );
    }

    interface Presenter extends BasePresenter {

        void saveConfiguration();

        void loadContentToHeader();
    }
}
