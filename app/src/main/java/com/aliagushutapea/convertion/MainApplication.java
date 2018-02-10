package com.aliagushutapea.convertion;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.aliagushutapea.convertion.dependencyinjection.component_database.ApplicationComponentDatabase;
import com.aliagushutapea.convertion.dependencyinjection.component_database.DaggerApplicationComponentDatabase;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.ApplicationComponentMVP;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerApplicationComponentMVP;
import com.aliagushutapea.convertion.dependencyinjection.module_database.ApplicationModuleDatabase;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.ApplicationModuleMVP;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ali on 24/01/18.
 */

public class MainApplication extends Application {
    private static final int PERMISSION_REQUEST_CODE = 200;
    ApplicationComponentMVP componentMVP;
    ApplicationComponentDatabase componentDatabase;

    public MainApplication() {
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkPermission();
        componentMVP = DaggerApplicationComponentMVP
                .builder()
                .applicationModuleMVP(new ApplicationModuleMVP(this))
                .build();

        componentDatabase = DaggerApplicationComponentDatabase
                .builder()
                .applicationModuleDatabase(new ApplicationModuleDatabase(this))
                .build();
    }

    private void checkPermission() {
        boolean isPermissionStorageGranted = ActivityCompat
                .checkSelfPermission(
                        MainApplication.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED;
        if (isPermissionStorageGranted) {
            initializeLeakCanary();
            return;
        }
    }

    private void initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public ApplicationComponentMVP getApplicationComponentMVP() {
        return componentMVP;
    }

    public ApplicationComponentDatabase getApplicationComponentDatabase(){
        return componentDatabase;
    }
}
