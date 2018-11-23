package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preorder_add extends AppCompatActivity {
    Button btnPreorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_add);
        btnPreorder = findViewById(R.id.btn_makepreorder);

        btnPreorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Preorder_add.this, "Pre-Order successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Preorder_add.this, Preorder.class);
                startActivity(intent);
            }
        });

    }
}
