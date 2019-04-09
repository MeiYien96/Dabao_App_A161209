package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Restaurant_available extends Activity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Restaurant_location> list;
    Restaurant_adapter adapter;
    Button btnViewOrder;
    boolean orderAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_available);

        btnViewOrder = findViewById(R.id.btn_vieworder);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Restaurant_location>();
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("DriversAvailable");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Restaurant_location r = dataSnapshot1.getValue(Restaurant_location.class);
                    list.add(r);
                }
                adapter = new Restaurant_adapter(Restaurant_available.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Restaurant_available.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");
                ref.orderByChild("hunterUid").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderAvailable = true;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        orderAvailable = false;
                    }
                });
                if(orderAvailable = true){
                    startActivity(new Intent(Restaurant_available.this, Popup_confirmation.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Order hasn't exist yet!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
