package com.cmy.dabao_app_a161209;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Food_tracking extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, RoutingListener {
    Button btnContact, btnReached;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    String orderUid,driverUid,from,to;
    LatLng origin,destination;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracking);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hunter_map);
        mapFragment.getMapAsync(this);
        btnContact = findViewById(R.id.btn_contact);
        polylines = new ArrayList<>();
        //btnReached = findViewById(R.id.btn_reached);

        /*btnReached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Food_tracking.this);
                builder.setCancelable(true);
                builder.setTitle("Food Reached");
                builder.setMessage("Hello, your food had reached, please take from driver!");
                builder.setPositiveButton("Received", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Food_tracking.this, Thanks.class));
                    }
                });
                builder.setNegativeButton("Contact Driver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Food_tracking.this, Food_tracking.class));
                    }
                });
                builder.show();
            }
        });*/
    }

    private void getDriverLocation(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");
        ref.orderByChild("hunterUid").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    orderUid = childSnapshot.getKey();
                    final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Order").child(orderUid);
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Order order = dataSnapshot.getValue(Order.class);
                            driverUid = order.getDriverUid();
                            from = order.getFrom();
                            to = order.getTo();
                            origin = getOrigin(from);
                            destination =getDestination(to);
                            getRouteToMarker(destination);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*DatabaseReference locationref = FirebaseDatabase.getInstance().getReference().child("DriversAvailableLocation").child(driverUid).child("l");
        locationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    List<Object> obj = (List<Object>)dataSnapshot.getValue();
                    double locationLat = 0;
                    double locationLong = 0;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    private void getRouteToMarker(LatLng destination) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(origin, destination)
                .build();
        routing.execute();
    }

    private LatLng getDestination(String t) {
        if(t.equals("Kolej Pendeta Za’ba")){
            destination = new LatLng(2.919514,101.774489);
        }
        else if(t.equals("Kolej Burhanuddin Helmi")){
            destination = new LatLng(2.927593,101.776949);
        }
        else if(t.equals("Kolej Keris Mas")){
            destination = new LatLng(2.926817,101.789284);
        }
        else if(t.equals("Kolej Dato’Onn")){
            destination = new LatLng(2.931418,101.780544);
        }
        else if(t.equals("Kolej Aminuddin Baki")){
            destination = new LatLng(2.924733,101.783970);
        }
        else if(t.equals("Kolej Ungku Omar")){
            destination = new LatLng(2.924727,101.779836);
        }
        else if(t.equals("Kolej Ibrahim Yaakub")){
            destination = new LatLng(2.924536,101.778223);
        }
        else if(t.equals("Kolej Rahim Kajai")){
            destination = new LatLng(2.932995,101.783355);
        }
        else if(t.equals("Kolej Ibu Zain")){
            destination = new LatLng(2.930280,101.783127);
        }
        else if(t.equals("Kolej Tun Hussein Onn")){
            destination = new LatLng(2.930255,101.779623);
        }
        return destination;
    }

    private LatLng getOrigin(String f) {
        if(f.equals("Restoran Al Fariz Maju")){
            origin = new LatLng(2.928220,101.767240);
        }
        else if (f.equals("大树下饭店 Kedai Makan Kita")){
            origin = new LatLng(2.927330,101.768140);
        }
        else if (f.equals("Mohammad Chan Restaurant")){
            origin = new LatLng(2.970819,101.776336);
        }
        else if (f.equals("Hot Meal Bar")){
            origin = new LatLng(2.930770,101.776990);
        }
        else if (f.equals("Restaurant homst")){
            origin = new LatLng(2.928190,101.767471);
        }
        return origin;
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
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HuntersAvailableLocation");
        GeoFire geofire = new GeoFire(ref);
        geofire.setLocation(userId, new GeoLocation(location.getLatitude(),location.getLongitude()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Food_tracking.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HuntersAvailableLocation");
        GeoFire geofire = new GeoFire(ref);
        geofire.removeLocation(userId);
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }
}
