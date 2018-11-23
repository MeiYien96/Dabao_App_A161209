package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preorder_request extends AppCompatActivity {
    Button btnView, btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_request);

        btnView = findViewById(R.id.btn_view);
        btnAccept = findViewById(R.id.btn_accept);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Preorder_request.this, Preorder_request_view.class);
                startActivity(intent);
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Preorder_request.this, "You had accept the preorder!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
