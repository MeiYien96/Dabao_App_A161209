package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preorder_request_view extends AppCompatActivity {
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_request_view);

        btnAccept = findViewById(R.id.btn_accept_preorder);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Preorder_request_view.this, "You had accept the preorder!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Preorder_request_view.this, driver_mainmenu.class);
                startActivity(intent);
            }
        });
    }
}
