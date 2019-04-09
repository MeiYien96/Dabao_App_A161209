package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class Popup_confirmation extends Activity {
    Button btnConfirm, btnCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_confirmation);

        btnConfirm = findViewById(R.id.btn_confirmorder);
        btnCancel = findViewById(R.id.btn_cancel);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9), (int)(height*.9));



        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Popup_confirmation.this, Food_tracking.class));
            }
        });

    }

}
