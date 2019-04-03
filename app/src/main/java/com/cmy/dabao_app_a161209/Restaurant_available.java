package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_available);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Restaurant_location>();

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
    }
}
