package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delivery_Process extends AppCompatActivity  {
    Button  btnAcceptOrder,btnDelivering,btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__process);

        btnAcceptOrder = findViewById(R.id.btn_accept_order);
        btnDelivering = findViewById(R.id.btn_delivering);
        btnComplete = findViewById(R.id.btn_complete);
        btnComplete.setEnabled(false);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Delivery_Process.this, Generate_order_details.class));
            }
        });

        btnDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnComplete.setEnabled(true);
                FirebaseDatabase.getInstance().getReference("DriversAvailable").child(userId).removeValue();
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DriversAvailableLocation");
                GeoFire geofire = new GeoFire(ref);
                geofire.removeLocation(userId);
                startActivity(new Intent(Delivery_Process.this, Thanks_driver.class));
            }
        });

    }

    }
