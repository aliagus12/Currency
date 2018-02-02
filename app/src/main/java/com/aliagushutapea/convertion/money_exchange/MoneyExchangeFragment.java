package com.aliagushutapea.convertion.money_exchange;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.databinding.FragmentMoneyExchangerBinding;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerMoneyExchangeComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MoneyExchangeFragmentPresenterModule;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.aliagushutapea.convertion.show_all_table.ShowAllListCurrencyFragment;
import com.aliagushutapea.convertion.utils.AutoResizeTextView;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Created by ali on 07/01/18.
 */

public class MoneyExchangeFragment extends Fragment implements MoneyExchangeFragmentContract.View {

    final String TAG = MoneyExchangeFragment.this.getClass().getSimpleName();
    PropertyMoney propertyMoney;
    public static final int CONTENT = 1;
    public static final int LOADING = 2;
    @Inject
    MoneyExchangeFragmentPresenter presenter;
    FragmentMoneyExchangerBinding binding;
    private Toast toast;

    public MoneyExchangeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMoneyExchangeComponent
                .builder()
                .moneyExchangeFragmentPresenterModule(new MoneyExchangeFragmentPresenterModule(this))
                .applicationComponentMVP(
                        ((MainApplication) getActivity().getApplication())
                                .getApplicationComponentMVP()
                )
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_money_exchanger,
                container,
                false
        );
        if (propertyMoney == null) {
            propertyMoney = PropertyMoney.getInstance();
        }
        View view = binding.getRoot();
        binding.setPropertyMoney(propertyMoney);
        binding.setPresenter(presenter);
        return view;
    }

    @Override
    public void makeToast(int message) {
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void showAllCurrency(View view) {
        ShowAllListCurrencyFragment listAllCurrency = new ShowAllListCurrencyFragment();
        switch (view.getId()) {
            case R.id.imageMoneyTarget:
                listAllCurrency.setFilter("target");
                break;
            case R.id.imageMoneyResult:
                listAllCurrency.setFilter("result");
                break;
        }
        listAllCurrency.setContext(getContext());
        listAllCurrency.show(
                getFragmentManager(),
                listAllCurrency.getTag()
        );
        onPause();
    }

    @Override
    public void loadDataToView(CurrencyModel target, CurrencyModel result) {
        setTextViewResult(result);
        binding.nameCurrencyTarget.setText(target.getSymbol());
        binding.nameCurrencyResult.setText(result.getSymbol());
        setImageView(binding.imageMoneyTarget, target);
        setImageView(binding.imageMoneyResult, result);
    }

    private void setImageView(ImageView imageView, CurrencyModel model) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getContext())
                .load(model.getImageCurrency())
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(imageView);
    }

    private void setTextViewResult(CurrencyModel result) {
        AutoResizeTextView autoResizeTextView = (AutoResizeTextView) getView().findViewById(R.id.txtCurrencyResult);
        autoResizeTextView.setMinTextSize(26.f);
        //autoResizeTextView.setText(result.getCurrencyName());
        autoResizeTextView.resizeText();
    }

}
