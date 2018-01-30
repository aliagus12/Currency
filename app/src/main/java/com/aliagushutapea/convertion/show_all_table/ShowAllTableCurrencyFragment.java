package com.aliagushutapea.convertion.show_all_table;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerShowAllTableCurrencyFragmentComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.ShowAllTableCurrencyFragmentModule;
import com.aliagushutapea.convertion.main_content.MainContentActivity;
import com.aliagushutapea.convertion.show_all_table.adapter.AdapterCurrencyExchange;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ali on 27/01/18.
 */

public class ShowAllTableCurrencyFragment extends BottomSheetDialogFragment implements ShowAllTableCurrencyFragmentContract.View,
        AdapterCurrencyExchange.ListenerAdapterCurrencyExchange {

    private Context context;
    @Inject
    ShowAllTableCurrencyFragmentPresenter mPresenter;
    private View view;
    @BindView(R.id.relatiflayout_container_show_all_currency)
    RelativeLayout relatifLayoutContainerFragmentShowAllCurrency;
    @BindView(R.id.recyclerViewListCurrency)
    RecyclerView recyclerViewListCurrency;
    private AdapterCurrencyExchange mAdapterCurrencyExchange;
    private View mView;
    private String filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerShowAllTableCurrencyFragmentComponent
                .builder()
                .showAllTableCurrencyFragmentModule(new ShowAllTableCurrencyFragmentModule(this))
                .applicationComponentMVP(
                        ((MainApplication) getActivity().getApplication())
                                .getApplicationComponentMVP()
                )
                .build()
                .inject(this);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        view = View.inflate(getContext(), R.layout.fragment_show_all_currency, null);
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
        setLayout();
        mPresenter.loadListCurrency();
    }

    private void setLayout() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        layoutParams.setMargins(50, 10, 50, 0);
        final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        assert behavior != null;
        ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //nothing
            }
        });
        relatifLayoutContainerFragmentShowAllCurrency.post(new Runnable() {
            @Override
            public void run() {
                int heightCoodinatorLayoutCountainerFragmentAddCurrency = relatifLayoutContainerFragmentShowAllCurrency
                        .getHeight();
                ((BottomSheetBehavior) behavior).setPeekHeight(heightCoodinatorLayoutCountainerFragmentAddCurrency);
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void makeToast(int message) {

    }

    @Override
    public void setAdapterCurrency(List<CurrencyModel> currencyModelList, List<Integer> listType) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                DividerItemDecoration.VERTICAL
        );
        mAdapterCurrencyExchange = new AdapterCurrencyExchange(currencyModelList, listType, this);
        mAdapterCurrencyExchange.setContext(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewListCurrency.setLayoutManager(linearLayoutManager);
        recyclerViewListCurrency.setHasFixedSize(true);
        recyclerViewListCurrency.addItemDecoration(dividerItemDecoration);
        recyclerViewListCurrency.setAdapter(mAdapterCurrencyExchange);
    }

    @Override
    public void onHolderClick(View view) {
        CurrencyModel currencyModel = (CurrencyModel) view.getTag();
        mPresenter.saveCurrencyModelToDatabase(currencyModel, filter);
        ((MainContentActivity)getActivity()).refresh();
        dismissAllowingStateLoss();
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
