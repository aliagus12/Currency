package com.aliagushutapea.convertion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerMainActivityComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MainActivityPresenterModule;
import com.aliagushutapea.convertion.main_content.MainContentActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final int PERMISSION_REQUEST_CODE = 200;
    @Inject
    MainActivityPresenter mainActivityPresenter;

    View mContentView;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainActivityPresenterModule(this);
        DaggerMainActivityComponent
                .builder()
                .mainActivityPresenterModule(new MainActivityPresenterModule(this))
                .applicationComponentMVP(
                        ((MainApplication)getApplication())
                        .getApplicationComponentMVP()
                )
                .build()
                .inject(this);
        mContentView = findViewById(R.id.mainContainer);
        mContentView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        hideNavigationBar();
                    }
                }
        );
        checkPermission();
    }

    private void checkPermission() {
        boolean isPermissionStorageGranted = ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        String[] listPermission = new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (!isPermissionStorageGranted) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    listPermission,
                    PERMISSION_REQUEST_CODE
            );
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toMainContent();
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void toMainContent() {
        Intent intent = new Intent(getApplicationContext(), MainContentActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void makeToast(int message) {

    }

}
