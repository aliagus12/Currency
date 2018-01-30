package com.aliagushutapea.convertion.utils;

import android.support.annotation.Nullable;

/**
 * Created by ali on 07/01/18.
 */

public class CheckingPrecondition {
    public CheckingPrecondition() {
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
