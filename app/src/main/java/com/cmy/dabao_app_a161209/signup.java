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
import android.widget.RadioButton;
import android.widget.TextView;

public class signup extends Activity {
    Button btnSignup;
    EditText etUsername, etPassword, etCpassword;
    RadioButton rbFoodhunter, rbFooddriver;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        btnSignup = findViewById(R.id.btn_signup);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.etpassword);
        etCpassword = findViewById(R.id.etcpassword);
        tvLogin = findViewById(R.id.tv_login);
        rbFoodhunter = findViewById(R.id.rb_food_hunter);
        rbFooddriver = findViewById(R.id.rb_food_driver);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etUsername.getText().toString())||TextUtils.isEmpty(etPassword.getText().toString())||TextUtils.isEmpty(etCpassword.getText().toString())){
                    if(TextUtils.isEmpty(etUsername.getText().toString())){
                        etUsername.setError("Username cannot be empty!");
                    }
                    if(TextUtils.isEmpty(etPassword.getText().toString())){
                        etPassword.setError("Password cannot be empty!");
                    }
                    if(TextUtils.isEmpty(etCpassword.getText().toString())){
                        etCpassword.setError("Confirm Password cannot be empty!");
                    }
                }
                //else if(!etPassword.getText().equals(etCpassword.getText())){
                    //etCpassword.setError("Password and confirm password are not same!");
                //}
                else{
                    if(rbFoodhunter.isChecked()){
                        Intent createIntent = new Intent(getApplicationContext(), hunter_mainmenu.class);
                        startActivityForResult(createIntent, 0);
                    }
                    else {
                        if(rbFooddriver.isChecked()) {
                            Intent typeIntent = new Intent(getApplicationContext(), driver_mainmenu.class);
                            startActivityForResult(typeIntent, 0);
                        }
                    }
                }
            }
        });

    }
}
