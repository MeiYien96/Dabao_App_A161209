package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class hunter_mainmenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button btnCancel, btnPreorder,btnSetting;
    TextView tvHunt;
    AnimationDrawable animation;
    //ImageView ivLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunter_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnPreorder = findViewById(R.id.btn_preorder);
        btnCancel = findViewById(R.id.btn_cancel);
        tvHunt = findViewById(R.id.tv_hunt);
        btnSetting = findViewById(R.id.btn_setting);

        //ivLoading = findViewById(R.id.iv_loading);
        //ImageView loading = (ImageView)findViewById(R.id.iv_loading);
        //loading.setBackgroundResource(R.drawable.loading);
        //animation = (AnimationDrawable)loading.getBackground();

        /*tvHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.stop();
            }
        });*/
        tvHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hunter_mainmenu.this, Restaurant_available.class);
                startActivity(intent);
            }
        });


        btnPreorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(hunter_mainmenu.this, Preorder.class);
               startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hunter_mainmenu.this, Hunter_Setting.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hunter_mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;



        //initializing the fragment object which is selected
        switch (itemId) {
            /*case R.id.nav_home:
                fragment = new Setting_Fragment();
                break;*/
            case R.id.nav_preorder:
                Intent intent = new Intent(hunter_mainmenu.this, Preorder_request.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            /*case R.id.nav_setting:
                fragment = new Setting_Fragment();
                break;
            case R.id.nav_support:
                fragment = new Setting_Fragment();
                break;
            case R.id.nav_bedriver:
                fragment = new Setting_Fragment();
                break;
            case R.id.nav_rateus:
                fragment = new Setting_Fragment();
                break;*/
        }

        //replacing the fragment
        if (fragment != null) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
            fl.removeAllViews();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment).commitNow();
            //ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
