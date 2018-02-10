package com.aliagushutapea.convertion.detail_currency;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerAddCurrencyFragmentComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.AddCurrencyFragmentModule;
import com.aliagushutapea.convertion.main_content.MainContentActivity;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ali on 25/01/18.
 */

public class DetailCurrencyFragment extends BottomSheetDialogFragment implements DetailCurrencyFragmentContract.View,
        View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private static final String TAG = DetailCurrencyFragment.class.getSimpleName();
    private Context context;
    private View view;
    @BindView(R.id.mainContainerAddCurrency)
    ConstraintLayout constraintLayoutContainerFragmentAddCurrency;
    @BindView(R.id.symbol_currency)
    TextView mTextViewSymbolCurrency;
    @BindView(R.id.name_currency)
    TextView mTextViewNameCurrency;
    @BindView(R.id.symbol_native)
    TextView mTextViewSymbolNative;
    @BindView(R.id.code)
    TextView mCode;
    @BindView(R.id.name_country)
    TextView mEditTextNameCountry;
    @BindView(R.id.imageAddCurrency)
    AppCompatImageView mImageViewCurrency;
    @BindView(R.id.imageCountry)
    AppCompatImageView mImageViewCountry;
    @BindView(R.id.default_toolbar)
    Toolbar toolbarAddCurrency;
    CurrencyModel mCurrencyModel;
    CoordinatorLayout.Behavior behavior;

    @Inject
    DetailCurrencyFragmentPresenter mPresenter;
    private static final int RESULT_LOAD_IMAGE_CURRENCY = 101;
    private Uri imageUriCurrency;
    private Toast toast;
    private CurrencyModel currency;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAddCurrencyFragmentComponent.builder()
                .addCurrencyFragmentModule(new AddCurrencyFragmentModule(this))
                .applicationComponentMVP(
                        ((MainApplication) getActivity().getApplication()).getApplicationComponentMVP()
                )
                .build()
                .inject(this);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        view = View.inflate(getContext(), R.layout.fragment_detail_currency, null);
        mCurrencyModel = new CurrencyModel();
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
        setStateDialog(view);
        setToolbar();
        loadData();
    }

    private void loadData() {
        if (currency != null) {
            mTextViewSymbolCurrency.setText("Symbol : " + currency.getSymbol());
            mTextViewNameCurrency.setText(currency.getName());
            mTextViewSymbolNative.setText("Symbol Native : " + currency.getSymbolNative());
            mCode.setText("Code : " + currency.getId());
            setImageView(currency.getImageCountry(), mImageViewCountry);
            setImageView(currency.getImageCurrency(), mImageViewCurrency);
            if (!currency.getCountry().equals("") && !currency.getCountry().equals("-")) {
                mEditTextNameCountry.setText(currency.getCountry());
            }
        }
    }

    private void setToolbar() {
        toolbarAddCurrency.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbarAddCurrency.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
        toolbarAddCurrency.inflateMenu(R.menu.detail_currency_menu);
        toolbarAddCurrency.getMenu().
                findItem(R.id.save).
                setOnMenuItemClickListener(this);
    }

    private void setStateDialog(View view) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        layoutParams.setMargins(50, 10, 50, 0);
        behavior = layoutParams.getBehavior();
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
        constraintLayoutContainerFragmentAddCurrency.post(new Runnable() {
            @Override
            public void run() {
                int heightCoodinatorLayoutCountainerFragmentAddCurrency = constraintLayoutContainerFragmentAddCurrency
                        .getHeight();
                ((BottomSheetBehavior) behavior).setPeekHeight(heightCoodinatorLayoutCountainerFragmentAddCurrency);
            }
        });
    }

    @Override
    public void makeToast(int message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @OnClick({
            R.id.imageAddCurrency
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageAddCurrency:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_CURRENCY);
                break;
        }
    }

    private void saveDataCurrency() {
        String countryName = checkNullable(mEditTextNameCountry, "-");

        String imagePathCurrency = !String.valueOf(imageUriCurrency).equals("null") ?
                imageUriCurrency.toString() : "-";
        if (imagePathCurrency.equals("-")) {
            imagePathCurrency = currency.getImageCurrency();
        }
        mPresenter.saveDataCurrencyToDataBase(
                currency.getId(),
                currency.getSymbol(),
                currency.getName(),
                countryName,
                currency.getSymbolNative(),
                currency.getImageCountry(),
                imagePathCurrency
        );
    }

    public String checkNullable(TextView text, String string) {
        String result = !text.getText().toString().equals("null") &&
                !text.getText().toString().equals("") &&
                !text.getText().toString().equals(null) &&
                text != null ?
                text.getText().toString() : string;
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE_CURRENCY:
                if (null != data) {
                    imageUriCurrency = data.getData();
                    mImageViewCurrency.setScaleType(ImageView.ScaleType.FIT_XY);
                    setImageView(imageUriCurrency.toString(), mImageViewCurrency);
                }
                break;

            default:
                break;
        }
    }

    private void setImageView(String imageUri, AppCompatImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(imageUri)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(imageView);
    }

    @Override
    public void dismissDialog() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ((MainContentActivity) getActivity()).refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setCurrency(CurrencyModel currency) {
        this.currency = currency;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        saveDataCurrency();
        return false;
    }
}
