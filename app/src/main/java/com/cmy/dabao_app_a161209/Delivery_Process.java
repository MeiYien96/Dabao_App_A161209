package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Delivery_Process extends AppCompatActivity {
    Button  btnOrdering, btnDelivering,btnArrived, btnContact, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__process);

        btnOrdering = findViewById(R.id.btn_ordering);
        btnDelivering = findViewById(R.id.btn_delivering);
        btnArrived = findViewById(R.id.btn_arrived);
        btnContact = findViewById(R.id.btn_contact);
        btnCancel = findViewById(R.id.btn_cancel);

        btnArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delivery_Process.this, Thanks_driver.class);
                startActivity(intent);
            }
        });
    }
}
