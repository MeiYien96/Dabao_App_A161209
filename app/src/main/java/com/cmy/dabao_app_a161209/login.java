package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends Activity {
    Button btnStart;
    TextView tvRegister;
    EditText etMail, etPassword;
    String mail, password, identity;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        btnStart = findViewById(R.id.btn_start);
        tvRegister = findViewById(R.id.tv_register);
        etMail = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        firebaseAuth = FirebaseAuth.getInstance();


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = etMail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                identity = radioButton.getText().toString().trim();
                if (valid()) {

                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                finish();
                                if(identity.equals("Food Hunter")){
                                    finish();
                                    startActivity(new Intent(login.this, hunter_mainmenu.class));
                                    Toast.makeText(login.this, "Welcome!", Toast.LENGTH_SHORT).show();


                                }
                                else if(identity.equals("Food Driver")){
                                    finish();
                                    startActivity(new Intent(login.this, driver_mainmenu.class));
                                    Toast.makeText(login.this, "Welcome !", Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(login.this, "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean valid() {
        Boolean result = false;

        if (mail.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Fill All The Fields", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    /*protected void onStart(){
        super.onStart();
        if(firebaseAuth.getCurrentUser()!= null){
            finish();

            if(FirebaseDatabase.getInstance().getReference().child("Users").child("Food Driver").getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                startActivity(new Intent(this,driver_mainmenu.class));
            }
            else
                startActivity(new Intent(this,hunter_mainmenu.class));
        }
    }*/

}
