package com.cmy.dabao_app_a161209;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Food_tracking extends AppCompatActivity {
    Button btnContact, btnReached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracking);

        btnContact = findViewById(R.id.btn_contact);
        btnReached = findViewById(R.id.btn_reached);

        btnReached.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}
