package com.cmy.dabao_app_a161209;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class driver_mainmenu extends  AppCompatActivity
        implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, AdapterView.OnItemSelectedListener {
    Button btnDelivery,btnGenerateOrder, btnOrder;
    int click = 0;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    String username,restaurantId,selectedRestaurant,foodTag1,foodTag2,profilePic;
    Double rlongitude, rlatitude;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mainmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driver_map);
        mapFragment.getMapAsync(this);


        btnDelivery = findViewById(R.id.btn_delivery);
        btnGenerateOrder = findViewById(R.id.btn_generate_order);

        final Spinner spinner = (Spinner) findViewById(R.id.spi_restaurant);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurant_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnGenerateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(driver_mainmenu.this, Generate_order_details.class));
            }
        });


        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click == 0){
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child("Food Driver").child(userId);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            username = user.getUsername();
                            selectedRestaurant = String.valueOf(spinner.getSelectedItem());
                            if(selectedRestaurant.equals("Restoran Al Fariz Maju")){
                                restaurantId = "R001";
                                rlatitude = 2.928220;
                                rlongitude = 101.767240;
                                foodTag1 = "Indian Cuisine";
                                foodTag2 = "Halal";
                            }
                            else if(selectedRestaurant.equals("大树下饭店 Kedai Makan Kita")){
                                restaurantId = "R002";
                                rlatitude = 2.927330;
                                rlongitude = 101.768140;
                                foodTag1 = "Chinese Cuisine";
                                foodTag2 = "Non-Halal";
                            }
                            else if(selectedRestaurant.equals("Mohammad Chan Restaurant")){
                                restaurantId = "R003";
                                rlatitude = 2.970819;
                                rlongitude = 101.776336;
                                foodTag1 = "Chinese Muslim";
                                foodTag2 = "Halal";
                            }
                            else if(selectedRestaurant.equals("Hot Meal Bar")){
                                restaurantId = "R004";
                                rlatitude = 2.930770;
                                rlongitude = 101.776990;
                                foodTag1 = "Chinese Muslim";
                                foodTag2 = "Halal";
                            }
                            else {
                                restaurantId = "R005";
                                rlatitude = 2.928190;
                                rlongitude = 101.767471;
                                foodTag1 = "Chinese Muslim";
                                foodTag2 = "Halal";
                            }
                            Restaurant_location restaurant_location = new Restaurant_location(username,restaurantId,selectedRestaurant,foodTag1,foodTag2,profilePic,rlatitude,rlongitude,btnOrder);
                            FirebaseDatabase.getInstance().getReference().child("DriversAvailable").child(userId).setValue(restaurant_location);
                            btnDelivery.setText("STOP DELIVERY");
                            click++;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if (click == 1){
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("DriversAvailable").child(userId).removeValue();
                    btnDelivery.setText("READY FOR DELIVERY");
                    click = 0;
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
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
                        Intent intent = new Intent(driver_mainmenu.this, Driver_Setting.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_behunter:
                        FirebaseAuth.getInstance().signOut();
                        Intent in = new Intent(driver_mainmenu.this, signup.class);
                        startActivity(in);
                        finish();
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(driver_mainmenu.this, login.class);
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

    @Override    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //for spinner item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        getMenuInflater().inflate(R.menu.driver_mainmenu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(driver_mainmenu.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
       /* LatLng Malaysia= new LatLng(4.210484,101.975766);
        mMap.addMarker(new MarkerOptions().position(Malaysia).title("Marker in Malaysia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Malaysia));*/
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DriversAvailableLocation");
        GeoFire geofire = new GeoFire(ref);
        geofire.setLocation(userId, new GeoLocation(location.getLatitude(),location.getLongitude()));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DriversAvailableLocation");
        GeoFire geofire = new GeoFire(ref);
        geofire.removeLocation(userId);
    }
}
