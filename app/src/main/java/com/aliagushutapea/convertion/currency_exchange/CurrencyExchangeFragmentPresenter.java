package com.aliagushutapea.convertion.currency_exchange;

import android.view.View;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.CurrencyUtils;
import com.aliagushutapea.convertion.utils.SourceString;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ali on 07/01/18.
 */

public class CurrencyExchangeFragmentPresenter implements CurrencyExchangeFragmentContract.Presenter {

    private static final String TAG = CurrencyExchangeFragmentPresenter.class.getSimpleName();
    private CurrencyExchangeFragmentContract.View view;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;
    private CurrencyUtils currencyUtils;

    @Inject
    public CurrencyExchangeFragmentPresenter(
            CurrencyExchangeFragmentContract.View view,
            CurrencyModel currencyModel,
            DatabaseManagerHelper databaseManagerHelper,
            CurrencyUtils currencyUtils
    ) {
        this.view = view;
        this.currencyModel = currencyModel;
        this.databaseManagerHelper = databaseManagerHelper;
        this.currencyUtils = currencyUtils;
    }


    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void loadData() {
        CurrencyModel target = databaseManagerHelper
                .getCurrencyModelWithoutKey(SourceString.TARGET_CURRENCY_COLOMN);
        CurrencyModel result = databaseManagerHelper
                .getCurrencyModelWithoutKey(SourceString.RESULT_CURRENCY_COLOMN);
        //requestToServer(target.getId(), result.getId());
        view.loadDataToView(target, result);
    }

    @Override
    public void onNumberChanges(final String number) {
        Observable<String> observable = currencyUtils.getObservableFetchData(number);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String stringResult) {
                        view.setTextViewResult(stringResult);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //to do request to server
    private void requestToServer(final String codeTarget, final String codeResult) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                subscribeRequestToServer(e, codeTarget, codeResult);
            }
        });

    }

    private void subscribeRequestToServer(
            ObservableEmitter<List<String>> e,
            String codeTarget,
            String codeResult
    ) {
        CurrencyModel model = databaseManagerHelper.getCurrencyModelWithoutKey(SourceString.TARGET_CURRENCY_COLOMN);
        String keyCodeTarget = model.getId();
        /*List<String> codeList = databaseManagerHelper.getAllFieldInColomn(
                SourceString.ALL_CURRENCY_COLOMN,
                SourceString.ALL_CURRENCY_COLOMN[7]
        );*/
    }

    public void showListCurrency(View view) {
        currencyModel.setKeyConfiguration("configuration");
        currencyModel.setValueConfiguration("content");
        databaseManagerHelper.saveConfiguration(
                SourceString.CONFIGURATION_COLOMN,
                currencyModel
        );
        this.view.showAllCurrency(view);
    }
}
