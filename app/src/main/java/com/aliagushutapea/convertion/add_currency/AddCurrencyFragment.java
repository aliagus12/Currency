package com.aliagushutapea.convertion.add_currency;

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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddCurrencyFragment extends BottomSheetDialogFragment implements AddCurrencyFragmentContract.View {

    private Context context;
    private View view;
    @BindView(R.id.mainContainerAddCurrency)
    ConstraintLayout constraintLayoutContainerFragmentAddCurrency;
    @BindView(R.id.addIdCurrency)
    AppCompatEditText mEditTextAddIdCurrency;
    @BindView(R.id.addNameCurrency)
    AppCompatEditText mEditTextAddNameCurrency;
    @BindView(R.id.addCountryName)
    AppCompatEditText mEditTextAddCountryName;
    AppCompatEditText mEditTextAddValueCurrency;
    @BindView(R.id.imageAddCurrency)
    AppCompatImageView mImageViewAddCurrency;
    @BindView(R.id.imageAddCountry)
    AppCompatImageView mImageViewAddCountry;
    @BindView(R.id.floatingAddImageCurrency)
    FloatingActionButton mFloatingActionButtonAddImageCurrency;
    CurrencyModel mCurrencyModel;

    @Inject
    AddCurrencyFragmentPresenter mPresenter;
    private static final int RESULT_LOAD_IMAGE_CURRENCY = 101;
    private static final int RESULT_LOAD_IMAGE_COUNTRY = 102;
    private static final int RESULT_OK = 200;
    private Uri imageUriCurrency;
    private Uri imageUriCountry;
    private Toast toast;

    public void setContext(Context context) {
        this.context = context;
    }

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
        view = View.inflate(getContext(), R.layout.fragment_add_currency, null);
        mCurrencyModel = new CurrencyModel();
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
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
            R.id.imageAddCurrency,
            R.id.imageAddCountry,
            R.id.btnCancelAddCurrency,
            R.id.btnOkAddCurrency
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageAddCurrency:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_CURRENCY);
                break;
            case R.id.imageAddCountry:
                Intent ii = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ii, RESULT_LOAD_IMAGE_COUNTRY);
                break;
            case R.id.btnOkAddCurrency:
                saveDataCurrency();
                break;
            case R.id.btnCancelAddCurrency:
                dismiss();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                break;
        }
    }

    private void saveDataCurrency() {
        String idCurrency = checkNullable(mEditTextAddIdCurrency, "-");
        String nameCurrency = checkNullable(mEditTextAddNameCurrency, "-");
        String countryName = checkNullable(mEditTextAddCountryName, "-");

        String imagePathCountry = !String.valueOf(imageUriCountry).equals("null") ?
                imageUriCountry.toString() : "-";

        String imagePathCurrency = !String.valueOf(imageUriCurrency).equals("null") ?
                imageUriCurrency.toString() : "-";

        mPresenter.saveDataCurrencyToDataBase(
                idCurrency,
                nameCurrency,
                countryName,
                imagePathCountry,
                imagePathCurrency
        );
    }

    public String checkNullable(EditText editText, String string) {
        String result = !editText.getText().toString().equals("null") &&
                !editText.getText().toString().equals("") &&
                !editText.getText().toString().equals(null) &&
                editText != null ?
                editText.getText().toString() : string;
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE_CURRENCY:
                if (null != data) {
                    imageUriCurrency = data.getData();
                    mImageViewAddCurrency.setScaleType(ImageView.ScaleType.FIT_XY);
                    setImageView(imageUriCurrency, mImageViewAddCurrency);
                }
                break;

            case RESULT_LOAD_IMAGE_COUNTRY:
                if (null != data) {
                    imageUriCountry = data.getData();
                    mImageViewAddCountry.setScaleType(ImageView.ScaleType.FIT_XY);
                    setImageView(imageUriCountry, mImageViewAddCountry);
                }
                break;
            default:
                break;
        }
    }

    private void setImageView(Uri imageUri, AppCompatImageView imageView) {
        Glide.with(context)
                .load(imageUri)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(imageView);
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ((MainContentActivity) getActivity()).refresh();
    }
}
