package com.aliagushutapea.convertion.show_all_table.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.model.CurrencyModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ali on 07/01/18.
 */

public class AdapterCurrencyExchange extends RecyclerView.Adapter<AdapterCurrencyExchange.ViewHolder>
implements View.OnClickListener {

    public static final int MONEY_EXCHANGE_CONTENT = 1;
    public static final int LOADING_CONTENT = 2;
    private ListenerAdapterCurrencyExchange mListener;
    public View view;
    private List<CurrencyModel> listCurrency;
    private List<Integer> listTypes;
    private Context context;

    public AdapterCurrencyExchange(
            List<CurrencyModel> listCurrency,
            List<Integer> listTypes,
            ListenerAdapterCurrencyExchange mListener
    ) {
        this.listCurrency = listCurrency;
        this.listTypes = listTypes;
        this.mListener = mListener;
    }

    @Override
    public AdapterCurrencyExchange.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MONEY_EXCHANGE_CONTENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_money_list, null);
            return new ViewHolderContent(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.app_bar_drawer, null);
            return new ViewHolderLoading(view);
        }
    }

    @Override
    public void onBindViewHolder(AdapterCurrencyExchange.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case MONEY_EXCHANGE_CONTENT:
                ViewHolderContent viewHolderContent = (ViewHolderContent) holder;
                CurrencyModel currencyModel = listCurrency.get(position);
                viewHolderContent.itemView.setTag(currencyModel);
                viewHolderContent.itemView.setOnClickListener(this);
                viewHolderContent.bind(currencyModel);
                break;

            case LOADING_CONTENT:
                ViewHolderLoading viewHolderLoading = (ViewHolderLoading) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listCurrency.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listTypes.get(position);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        mListener.onHolderClick(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageCountry;
        private final TextView txtNameCountry;
        private final ImageView imageCurrency;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNameCountry = (TextView) itemView.findViewById(R.id.nameCurrency);
            imageCountry = (ImageView) itemView.findViewById(R.id.countryImage);
            imageCurrency = (ImageView) itemView.findViewById(R.id.imageCurrency);
        }

        public void bind(CurrencyModel currencyModel) {
            String imagePathCountry = currencyModel.getCurrencyCountryPath();
            String imagePathCurrency = currencyModel.getCurrencyImagePath();
            String nameCountry = currencyModel.getCurrencyName();
            setImageView(imagePathCurrency, imageCurrency);
            setImageView(imagePathCountry, imageCountry);
            txtNameCountry.setText(nameCountry);
        }

        private void setImageView(String imagePath, ImageView imageView) {
            Glide.with(context)
                    .load(imagePath)
                    .error(R.drawable.ic_broken_image_grey_24dp)
                    .into(imageView);
        }
    }

    public class ViewHolderContent extends ViewHolder {

        public ViewHolderContent(View itemView) {
            super(itemView);

        }
    }

    public class ViewHolderLoading extends ViewHolder {
        public ViewHolderLoading(View itemView) {
            super(itemView);
        }
    }

    public interface ListenerAdapterCurrencyExchange {
        void onHolderClick(View view);
    }
}
