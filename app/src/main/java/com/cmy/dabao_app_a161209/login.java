package com.cmy.dabao_app_a161209;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private ProgressBar spinner;

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
        spinner = (ProgressBar)findViewById(R.id.spi_loading);
        firebaseAuth = FirebaseAuth.getInstance();
        spinner.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(login.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(login.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                builder.setTitle("Require Location Permission");
                builder.setTitle("This app require location permission to get the location information.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(login.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                0);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(login.this, "Sorry, this app cannot be worked until the Location Permission is granded.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(login.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

        }
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
                spinner.setVisibility(View.VISIBLE);
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
                                    spinner.setVisibility(View.GONE);
                                    startActivity(new Intent(login.this, hunter_mainmenu.class));
                                    Toast.makeText(login.this, "Welcome!", Toast.LENGTH_SHORT).show();


                                }
                                else if(identity.equals("Food Driver")){
                                    finish();
                                    spinner.setVisibility(View.GONE);
                                    startActivity(new Intent(login.this, driver_mainmenu.class));
                                    Toast.makeText(login.this, "Welcome !", Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    spinner.setVisibility(View.GONE);
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
            }
            else
                startActivity(new Intent(this,login.class));

    }*/

}
