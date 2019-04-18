package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delivery_Process extends AppCompatActivity  {
    Button  btnDelivering,btnArrived, btnContact, btnCancel;
    String orderUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__process);

        btnDelivering = findViewById(R.id.btn_delivering);
        btnArrived = findViewById(R.id.btn_arrived);
        btnContact = findViewById(R.id.btn_contact);
        btnCancel = findViewById(R.id.btn_cancel);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");
                ref.orderByChild("driverUid").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                            orderUid = childSnapshot.getKey();
                            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Order").child(orderUid).child("hunterUid");
                            ref1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ref1.child("status").setValue("Delivering");
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
            }
        });
        btnArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    }
