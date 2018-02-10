package com.aliagushutapea.convertion;

import android.content.Context;

import com.aliagushutapea.convertion.database_helper.DatabaseManagerHelper;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.utils.CurrencyUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ali on 06/01/18.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {


    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private MainActivityContract.View view;
    private CurrencyModel currencyModel;
    private DatabaseManagerHelper databaseManagerHelper;
    private Context context;
    private CurrencyUtils currencyUtils;

    @Inject
    public MainActivityPresenter(
            MainActivityContract.View view,
            CurrencyModel currencyModel,
            DatabaseManagerHelper databaseManagerHelper,
            Context context,
            CurrencyUtils currencyUtils
    ) {
        this.view = view;
        this.currencyModel = currencyModel;
        this.databaseManagerHelper = databaseManagerHelper;
        this.context = context;
        this.currencyUtils = currencyUtils;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void insertDataToDatabase() {
        Observable<String> stringObservable = currencyUtils.getObservableInsertDataToDatabase(currencyModel);
        stringObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //todo nothing
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        try {
                            insertDataCurrencyToDatabase();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void insertDataCurrencyToDatabase() throws Exception {
        Observable<JSONObject> objectObservable = currencyUtils.getObservableInsertDataToDatabase();
        objectObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        //JSONArray jsonArrayItemDataAll = null;
                        try {
                            /*jsonArrayItemDataAll = jsonObject.getJSONArray("JOD");
                            Log.d(TAG, "jsonArray" + jsonArrayItemDataAll);*/
                            //insertDataCurrencyToDatabase(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //insertData(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        view.toMainContentAndDissmisProgressDialog();
                    }
                });


    }

    private void insertDataCurrencyToDatabase(JSONObject jsonObject) {
        //Observable observable = currencyUtils.getObservableInsertData(jsonObject);
    }

}
