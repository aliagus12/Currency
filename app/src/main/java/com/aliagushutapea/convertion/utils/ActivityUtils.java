package com.aliagushutapea.convertion.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.aliagushutapea.convertion.utils.CheckingPrecondition.checkNotNull;

/**
 * Created by ali on 07/01/18.
 */

public class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    public static void addFragmentToActivity(
            FragmentManager fragmentManager,
            Fragment fragment,
            int fragmentId
    ) {
        checkNotNull(fragmentManager, TAG + " FragmentManager Null");
        checkNotNull(fragment, TAG + " Fragment Null");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentId, fragment);
        transaction.commitAllowingStateLoss();
    }
}
