package com.aliagushutapea.convertion.show_all_table;

import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

import java.util.List;

/**
 * Created by ali on 27/01/18.
 */

public interface ShowAllListCurrencyFragmentContract {
    interface View extends BaseView{

        void setAdapterCurrency(List<CurrencyModel> currencyModelList, List<Integer> listType);

        void refreshAdapter(List<CurrencyModel> currencyModelList, List<Integer> listType);
    }

    interface Presenter extends BasePresenter{

        void loadListCurrency();

        void saveCurrencyModelToDatabase(CurrencyModel currencyModel, String filter);

        void querySearch(String newText);
    }
}
