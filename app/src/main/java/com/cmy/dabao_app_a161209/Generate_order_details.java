package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Generate_order_details extends AppCompatActivity {
    Button btnCalculate, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order_details);

        btnCalculate = findViewById(R.id.btn_calculate);
        btnConfirm = findViewById(R.id.btn_confirm_order);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Generate_order_details.this, Delivery_Process.class);
                startActivity(intent);
            }
        });
    }
}
