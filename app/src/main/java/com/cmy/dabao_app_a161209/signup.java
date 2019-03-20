package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.constraint.Constraints.TAG;


public class signup extends Activity {
    Button btnSignup;
    EditText etMail,etUsername, etPassword,etPhone;
    TextView tvLogin;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String email,username,password,phone,identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);

        btnSignup = findViewById(R.id.btn_signup);
        etMail = findViewById(R.id.et_mail);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.etpassword);
        etPhone = findViewById(R.id.et_phone);
        tvLogin = findViewById(R.id.tv_login);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                email = etMail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();

                radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                identity = radioButton.getText().toString().trim();

                if (valid()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                if(identity.equals("Food Hunter")){

                                    User user = new User(username, email, phone,identity);
                                    FirebaseDatabase.getInstance().getReference().child("Users").child("Food Hunter").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Signed Up Successful", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent i = new Intent(getApplicationContext(), hunter_mainmenu.class);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to start interface.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    User user = new User(username, email, phone,identity);
                                    FirebaseDatabase.getInstance().getReference().child("Users").child("Food Driver").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Signed Up Successful", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent i = new Intent(getApplicationContext(), driver_mainmenu.class);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to start interface.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            } else {
                                Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                                Toast.makeText(getApplicationContext(), "Error in Sign Up", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }

        });
    }
    private boolean valid() {
        Boolean result=false;

        if (username.equals("")||password.equals("")||email.equals("")||phone.equals("")) {
            Toast.makeText(getApplicationContext(),"Fill All The Fields",Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }

        return result;
    }

}
