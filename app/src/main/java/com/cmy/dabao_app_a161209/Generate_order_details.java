package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class Generate_order_details extends AppCompatActivity {
    Button btnCalculate, btnConfirm;
    EditText etDriverName, etFrom, etTo, etFoodPrice, etDeliveryFee, etTotal;
    String hunterName, driverName, from, to,foodprice,hunterUid;
    Double foodPrice, deliveryFee, total;
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order_details);

        etDriverName = findViewById(R.id.et_driverName);
        etFrom = findViewById(R.id.et_from);
        etTo = findViewById(R.id.et_to);
        etFoodPrice = findViewById(R.id.et_foodPrice);
        etDeliveryFee = findViewById(R.id.et_deliveryFee);
        etTotal = findViewById(R.id.et_total);
        etDeliveryFee.setEnabled(false);
        etTotal.setEnabled(false);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnConfirm = findViewById(R.id.btn_send_order);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usernameRef = FirebaseDatabase.getInstance().getReference().child("DriversAvailable").child(userId).child("huntersHandle");
        usernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> huntername = new ArrayList<String>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot hunterSnapshot: dataSnapshot.getChildren()) {
                        String hunterUsername = hunterSnapshot.getValue(String.class);
                        huntername.add(hunterUsername);
                    }
                    Spinner spiHunter = (Spinner) findViewById(R.id.spi_hunter);
                    ArrayAdapter<String> huntersAdapter = new ArrayAdapter<String>(Generate_order_details.this, android.R.layout.simple_spinner_item, huntername);
                    huntersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spiHunter.setAdapter(huntersAdapter);

                    spiHunter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            final String selected = huntername.get(position);
                            Toast.makeText(Generate_order_details.this, selected+" is selected!", Toast.LENGTH_SHORT).show();

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DriversAvailable").child(userId);
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Restaurant_location driver = dataSnapshot.getValue(Restaurant_location.class);
                                    etDriverName.setText(driver.getUsername());
                                    driverName = driver.getUsername();
                                    etFrom.setText(driver.getRestaurantName());
                                    from = driver.getRestaurantName();
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HunterRequest");
                                    ref.orderByChild("username").equalTo(selected).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                                hunterUid = childSnapshot.getKey();
                                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("HunterRequest").child(hunterUid);
                                                ref1.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        College_location hunter = dataSnapshot.getValue(College_location.class);
                                                        etTo.setText(hunter.getCollegeName());
                                                        to = hunter.getCollegeName();
                                                        //etHunterName.setText(hunter.getUsername());
                                                        //hunterName = hunter.getUsername();

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

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                foodprice = etFoodPrice.getText().toString();
                foodPrice = parseDouble(foodprice);
                deliveryFee = Double.valueOf(df.format(foodPrice * 0.20));
                total = Double.valueOf(df.format(foodPrice + deliveryFee));
                etDeliveryFee.setText(deliveryFee.toString());
                etTotal.setText(total.toString());
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order(userId,driverName,hunterUid, hunterName, from,to, foodPrice,deliveryFee,total);
                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Order").push();
                df.setValue(order);
                Intent intent = new Intent(Generate_order_details.this, Delivery_Process.class);
                startActivity(intent);
            }
        });
    }
}
