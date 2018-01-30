package com.aliagushutapea.convertion.temperature_exchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliagushutapea.convertion.R;

/**
 * Created by ali on 07/01/18.
 */

public class TemperatureExchange extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperatur_exchange, container, false);
        return view;
    }
}
