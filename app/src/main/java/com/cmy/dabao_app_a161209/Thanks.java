package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Thanks extends AppCompatActivity {
    Button btnRateus, btnMainmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        btnRateus = findViewById(R.id.btn_rateus);
        btnMainmenu = findViewById(R.id.btn_mainmenu);

        btnMainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Thanks.this, hunter_mainmenu.class));
            }
        });
    }
}
