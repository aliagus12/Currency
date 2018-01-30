package com.aliagushutapea.convertion.add_table_currency;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerAddTableCurrencyFragmentComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.AddTableCurrencyFragmentModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ali on 27/01/18.
 */

public class AddTableCurrencyFragment extends BottomSheetDialogFragment implements AddTableCurrencyFragmentContract.View{

    @Inject
    AddTableCurrencyFragmentPresenter mPresenter;
    private View view;
    private Context context;
    @BindView(R.id.mainContainerAddTableCurrency)
    ConstraintLayout coordinatorLayoutContainerFragmentAddTableCurrency;
    @BindView(R.id.edtTableName)
    AppCompatEditText edtTableName;
    private Toast toast;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAddTableCurrencyFragmentComponent
                .builder()
                .addTableCurrencyFragmentModule(new AddTableCurrencyFragmentModule(this))
                .applicationComponentMVP(
                        ((MainApplication)getActivity().getApplication()).getApplicationComponentMVP()
                )
                .build()
                .inject(this);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        view = View.inflate(getContext(), R.layout.fragment_add_table_currency, null);
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        layoutParams.setMargins(0, 10, 0, 0);
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
        coordinatorLayoutContainerFragmentAddTableCurrency.post(new Runnable() {
            @Override
            public void run() {
                int heightCoodinatorLayoutCountainerFragmentAddCurrency = coordinatorLayoutContainerFragmentAddTableCurrency
                        .getHeight();
                ((BottomSheetBehavior) behavior).setPeekHeight(heightCoodinatorLayoutCountainerFragmentAddCurrency);
            }
        });
    }

    @OnClick({
            R.id.btnOkAddTableCurrency,
            R.id.btnCancelAddTableCurrency
    })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnCancelAddTableCurrency:
                dismissAllowingStateLoss();
                break;
            case R.id.btnOkAddTableCurrency:
                String nameTable = edtTableName.getText().toString();
                mPresenter.addTableToDatabase(nameTable);
               break;
        }
    }

    @Override
    public void makeToast(int message) {
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void dismissDialog() {
        dismissAllowingStateLoss();
    }
}
