package com.cmy.dabao_app_a161209;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Thanks_driver extends AppCompatActivity {
    Button btnMainmenu,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_driver);

        btnMainmenu = findViewById(R.id.btn_mainmenu);
        btnExit = findViewById(R.id.btn_exit);

        btnMainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Thanks_driver.this, driver_mainmenu.class));
            }
        });
    }
}
