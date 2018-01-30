package com.aliagushutapea.convertion.add_table_currency;

import com.aliagushutapea.convertion.base.BasePresenter;
import com.aliagushutapea.convertion.base.BaseView;

/**
 * Created by ali on 27/01/18.
 */

public interface AddTableCurrencyFragmentContract {

    interface View extends BaseView{

        void dismissDialog();
    }

    interface Presenter extends BasePresenter{

        void addTableToDatabase(String nameTable);
    }
}
