package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends Activity {
    Button btnStart;
    TextView tvRegister;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        btnStart = findViewById(R.id.btn_start);
        tvRegister = findViewById(R.id.tv_register);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etUsername.getText().toString())||TextUtils.isEmpty(etPassword.getText().toString())){
                    if(TextUtils.isEmpty(etUsername.getText().toString())){
                        etUsername.setError("Username cannot be empty!");
                    }
                    if(TextUtils.isEmpty(etPassword.getText().toString())){
                        etPassword.setError("Password cannot be empty!");
                    }

                }
                else{
                    Intent intent = new Intent(login.this, hunter_mainmenu.class);
                    startActivity(intent);
                }
            }
        });

    }
}
