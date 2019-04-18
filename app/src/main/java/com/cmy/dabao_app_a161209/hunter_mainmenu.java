package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class hunter_mainmenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button btnHunt;
    ActionBarDrawerToggle toggle;
    int click = 0;
    String selectedCollege,driverUid,username;
    Double rlongitude, rlatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunter_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnHunt = findViewById(R.id.btn_hunt);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb_progressBar);
        progressBar.setVisibility(View.GONE);

        final Spinner spinner = (Spinner) findViewById(R.id.spi_college);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.college_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click == 0){
                    progressBar.setVisibility(View.VISIBLE);
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Food Hunter").child(userId);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            username = user.getUsername();
                            selectedCollege = String.valueOf(spinner.getSelectedItem());
                            if(selectedCollege.equals("Kolej Pendeta Za’ba")){
                                rlatitude = 2.919514;
                                rlongitude = 101.774489;
                            }
                            else if(selectedCollege.equals("Kolej Burhanuddin Helmi")){
                                rlatitude = 2.927593;
                                rlongitude = 101.776949;
                            }
                            else if(selectedCollege.equals("Kolej Keris Mas")){
                                rlatitude = 2.926817;
                                rlongitude = 101.789284;
                            }
                            else if(selectedCollege.equals("Kolej Dato’Onn")){
                                rlatitude = 2.931418;
                                rlongitude = 101.780544;
                            }
                            else if(selectedCollege.equals("Kolej Aminuddin Baki")){
                                rlatitude = 2.924733;
                                rlongitude = 101.783970;
                            }
                            else if(selectedCollege.equals("Kolej Ungku Omar")){
                                rlatitude = 2.924727;
                                rlongitude = 101.779836;
                            }
                            else if(selectedCollege.equals("Kolej Ibrahim Yaakub")){
                                rlatitude = 2.924536;
                                rlongitude = 101.778223;
                            }
                            else if(selectedCollege.equals("Kolej Rahim Kajai")){
                                rlatitude = 2.932995;
                                rlongitude = 101.783355;
                            }
                            else if(selectedCollege.equals("Kolej Ibu Zain")){
                                rlatitude = 2.930280;
                                rlongitude = 101.783127;
                            }
                            else {
                                rlatitude = 2.930255;
                                rlongitude = 101.779623;
                            }
                            College_location college_location = new College_location(selectedCollege,driverUid,username,rlatitude,rlongitude);
                            FirebaseDatabase.getInstance().getReference().child("HunterRequest").child(userId).setValue(college_location);
                            btnHunt.setText("STOP HUNTING");
                            click++;
                            Intent in = new Intent(hunter_mainmenu.this, Restaurant_available.class);
                            startActivity(in);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else if (click == 1){
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("HunterRequest").child(userId).removeValue();
                    progressBar.setVisibility(View.GONE);
                    btnHunt.setText("HUNT FOR FOOD");
                    click = 0;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_setting:
                        Intent intent = new Intent(hunter_mainmenu.this, Hunter_Setting.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_behunter:
                        FirebaseAuth.getInstance().signOut();
                        Intent in = new Intent(hunter_mainmenu.this, signup.class);
                        startActivity(in);
                        finish();
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(hunter_mainmenu.this, login.class);
                        startActivity(i);
                        finish();
                }
                DrawerLayout d1=(DrawerLayout)findViewById(R.id.drawer_layout);
                if(d1.isDrawerOpen(GravityCompat.START)){
                    d1.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

    }
    //for spinner item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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


}
