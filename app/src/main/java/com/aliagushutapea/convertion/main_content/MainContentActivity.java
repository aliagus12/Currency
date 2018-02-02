package com.aliagushutapea.convertion.main_content;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.aliagushutapea.convertion.MainApplication;
import com.aliagushutapea.convertion.R;
import com.aliagushutapea.convertion.detail_currency.DetailCurrencyFragment;
import com.aliagushutapea.convertion.add_table_currency.AddTableCurrencyFragment;
import com.aliagushutapea.convertion.database_helper.InspectionDatabase;
import com.aliagushutapea.convertion.databinding.ActivityMainContentBinding;
import com.aliagushutapea.convertion.dependencyinjection.component_mpv.DaggerMainContentActivityComponent;
import com.aliagushutapea.convertion.dependencyinjection.module_mpv.MainContentActivityPresenterModule;
import com.aliagushutapea.convertion.money_exchange.MoneyExchangeFragment;
import com.aliagushutapea.convertion.show_all_table.ShowAllListCurrencyFragment;
import com.aliagushutapea.convertion.temperature_exchange.TemperatureExchange;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainContentActivity extends AppCompatActivity implements MainContentActivityContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainContentActivityPresenter presenter;
    private Toolbar toolbarAppbar;
    private ActivityMainContentBinding dataBinding;
    private Menu menuNav;
    private Menu menuNavBottom;
    private MenuItem mChanger;
    private BottomNavigationView bottomNavigationView;
    private static final String TAG = MainContentActivity.class.getSimpleName();
    private Toast toast;
    private DetailCurrencyFragment detailCurrencyFragment;
    private AddTableCurrencyFragment addTableCurrencyFragment;
    private ShowAllListCurrencyFragment showAllListCurrencyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainContentActivityComponent
                .builder()
                .mainContentActivityPresenterModule(new MainContentActivityPresenterModule(this))
                .applicationComponentMVP(
                        ((MainApplication) getApplication())
                                .getApplicationComponentMVP()
                )
                .build()
                .inject(this);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_content);
        dataBinding.setPresenter(presenter);
        setUpDrawer();
        setUpBottomNavigation();
    }

    private void setUpDrawer() {
        toolbarAppbar = dataBinding.includeAppBar.includeToolbar.defaultToolbar;
        setSupportActionBar(toolbarAppbar);
        toolbarAppbar.setTitle(R.string.money_changer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                dataBinding.drawerLayoutMain,
                toolbarAppbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        dataBinding.drawerLayoutMain.addDrawerListener(toggle);
        toggle.syncState();
        dataBinding.navigationDrawer.setNavigationItemSelectedListener(this);
        menuNav = dataBinding.navigationDrawer.getMenu();
    }

    private void setUpBottomNavigation() {
        bottomNavigationView = dataBinding
                .includeAppBar
                .includeContent
                .bottomNavigationViewDrawer;
        menuNavBottom = bottomNavigationView.getMenu();
        mChanger = menuNavBottom.findItem(R.id.botton_navigation_money_exchange);
        bottomNavigationViowGetTreeObserver(menuNavBottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void bottomNavigationViowGetTreeObserver(final Menu menuNavBottom) {
        final ArrayList<View> menuItems = new ArrayList<>(2);
        bottomNavigationView.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                bottomNavigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                for (int a = 0; a < 2; a++) {
                                    String id = "";
                                    if (a == 0) {
                                        id = "botton_navigation_money_exchange";
                                    } else if (a == 1) {
                                        id = "botton_navigation_temperature_exchange";
                                    }
                                    MenuItem menuItem = menuNavBottom.findItem(getResources().getIdentifier(id, "id", getPackageName()));
                                    Log.d("menuitem", String.valueOf(menuItem));
                                    bottomNavigationView.findViewsWithText(menuItems, menuItem.getTitle(), View.FIND_VIEWS_WITH_TEXT);
                                }
                                for (View menuItem : menuItems) {
                                    ((TextView) menuItem).setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
                                }
                            }
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_content_activity_drawer, new MoneyExchangeFragment())
                .commitAllowingStateLoss();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (dataBinding.drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayoutMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void makeToast(int message) {
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //for navigation drawer
        switch (item.getItemId()) {
            case R.id.menu_navigation_drawer_money:
                toolbarAppbar.setTitle(R.string.money_exchange);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_content_activity_drawer, new MoneyExchangeFragment())
                        .commitAllowingStateLoss();
                break;
            case R.id.menu_navigation_drawer_temperature:
                toolbarAppbar.setTitle(R.string.temperature_exchange);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_content_activity_drawer, new TemperatureExchange())
                        .commitAllowingStateLoss();
                break;
            case R.id.menu_navigation_drawer_add_table:
                addTableCurrencyFragment = new AddTableCurrencyFragment();
                addTableCurrencyFragment.setContext(getApplicationContext());
                addTableCurrencyFragment.show(
                        getSupportFragmentManager(),
                        addTableCurrencyFragment.getTag()
                );
                break;
            case R.id.menu_navigation_drawer_show_all_table:
                presenter.saveConfiguration();
                showAllListCurrencyFragment = new ShowAllListCurrencyFragment();
                showAllListCurrencyFragment.setContext(getApplicationContext());
                showAllListCurrencyFragment.show(
                        getSupportFragmentManager(),
                        showAllListCurrencyFragment.getTag()
                );
                break;
        }
        dataBinding.drawerLayoutMain.closeDrawer(GravityCompat.START);

        //for button navigation
        switch (item.getItemId()){
            case R.id.botton_navigation_money_exchange:
                toolbarAppbar.setTitle(R.string.money_exchange);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_content_activity_drawer, new MoneyExchangeFragment())
                        .commitAllowingStateLoss();
                return true;
            case R.id.botton_navigation_temperature_exchange:
                toolbarAppbar.setTitle(R.string.temperature_exchange);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_content_activity_drawer, new TemperatureExchange())
                        .commitAllowingStateLoss();
                return true;
        }
        return false;
    }

    @Override
    public void attachFragmentAddCurrency() {
        detailCurrencyFragment = new DetailCurrencyFragment();
        detailCurrencyFragment.setContext(getApplicationContext());
        detailCurrencyFragment.show(
                getSupportFragmentManager(),
                detailCurrencyFragment.getTag()
        );
    }

    @Override
    public void inspecDatabase() {
        Intent intent = new Intent(MainContentActivity.this, InspectionDatabase.class);
        startActivity(intent);
    }

    @Override
    public void detailCurrency(String currencyId) {
        /*Intent intent = new Intent(MainContentActivity.this, DetailsCurrency.class);
        intent.putExtra("key", currencyId);
        startActivity(intent);*/
    }

    public void refresh() {
        onResume();
    }

    public void showDialog(){

    }
}
