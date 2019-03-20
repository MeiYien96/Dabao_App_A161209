package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hunter_Setting extends Activity {
    Button btnEdit, btnSave;
    String username, matrix, phone, email;
    EditText etUsername, etMatrix, etPhone, etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting_);

        btnEdit = findViewById(R.id.btn_edit);
        etUsername = findViewById(R.id.et_username);
        etMatrix = findViewById(R.id.et_matric);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Food Hunter");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    username = ds.child("username").getValue(String.class);
                    phone = ds.child("phone").getValue(String.class);
                    email =  ds.child("email").getValue(String.class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hunter_Setting.this, hunter_mainmenu.class));
            }
        });

    }

}
