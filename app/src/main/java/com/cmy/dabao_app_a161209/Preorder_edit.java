package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Preorder_edit extends AppCompatActivity {
    Button btnEdit, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_edit);

        btnEdit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Preorder_edit.this, "Save successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Preorder_edit.this, Preorder.class);
                startActivity(intent);
            }
        });
    }
}
