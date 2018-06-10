package com.android.abhishek.megamovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.abhishek.megamovies.fragments.MovieFragment;
import com.android.abhishek.megamovies.fragments.TvFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolBarAtMainAct) Toolbar toolbar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navView) NavigationView navigationView;

    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setNavigationView();
        networkStatus();

        getSupportFragmentManager().beginTransaction().add(R.id.mainActFragmentLayout,new MovieFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.navMv :
                if(flag!=0){
                    setMovieFragment();
                }
                flag = 0;
                break;
            case R.id.navTv :
                if(flag!=1){
                    setTvFragment();
                }
                flag = 1;
                break;
            case R.id.navFav :
                if(flag!=2){
                    setFavFragment();
                }
                flag = 2;
                break;
            case R.id.navSetting :
                if(flag!=3){
                    setSettingFragment();
                }
                flag = 3;
                break;
            case R.id.navExit :
                finish();
            case R.id.navShare :
                if(flag!=4){
                    shareApp();
                }
                flag = 4;
                break;
            case R.id.navRate :
                if(flag!=5){
                    rateApp();
                }
                flag = 5;
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationView(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void networkStatus(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()){
        }
        else {
            showError();
        }
    }

    private void setMovieFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActFragmentLayout,new MovieFragment()).commit();
    }

    private void setFavFragment(){

    }

    private void setSettingFragment(){

    }

    private void rateApp(){

    }

    private void shareApp(){

    }

    private void setTvFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActFragmentLayout,new TvFragment()).commit();
    }

    private void showError(){

    }
}
