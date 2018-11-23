package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class Popup_confirmation extends Activity {
    Button btnConfirm, btnRecalculate, btnCancel;
    Dialog myDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_confirmation);
        myDialog = new Dialog(this);

        btnConfirm = findViewById(R.id.btn_confirmorder);
        btnRecalculate = findViewById(R.id.btn_recalculate);
        btnCancel = findViewById(R.id.btn_cancel);


        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.8));
       */


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Popup_confirmation.this, Food_tracking.class));
            }
        });

    }
    public void ShowPopup(View v) {
        ImageView ivNotice;
        myDialog.setContentView(R.layout.activity_popup_confirmation);

        ivNotice = (ImageView)myDialog.findViewById(R.id.iv_notice);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
