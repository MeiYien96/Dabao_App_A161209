package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Driver_Setting extends Activity {
    Button btnEdit, btnSave;
    String username, matrix, phone, email;
    EditText etUsername, etMatrix, etPhone, etEmail;
    ImageView profilePic;
    StorageReference storageReference;
    public static final int IMAGE_REQUEST = 1;
    private Uri imageUrl;
    private StorageTask uploadTask;
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__setting);

        etUsername = findViewById(R.id.et_username);
        etMatrix = findViewById(R.id.et_matric);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etUsername.setEnabled(false);
        etMatrix.setEnabled(false);
        etPhone.setEnabled(false);
        etEmail.setEnabled(false);

        profilePic = findViewById(R.id.iv_profile_pic);
        btnEdit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);

        storageReference = FirebaseStorage.getInstance().getReference("Profile_Picture");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child("Food Driver").child(userId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User driver = dataSnapshot.getValue(User.class);
                etUsername.setText(driver.getUsername());
                etMatrix.setText(driver.getMatric());
                etPhone.setText(driver.getPhone());
                etEmail.setText(driver.getEmail());
                if(driver.getProfilePic() == null){
                    profilePic.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
                else{
                    Glide.with(Driver_Setting.this)
                            .load(driver.getProfilePic()).placeholder(R.drawable.ic_account_circle_black_24dp)
                            .into(profilePic);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUsername.setEnabled(true);
                etMatrix.setEnabled(true);
                etPhone.setEnabled(true);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                matrix = etMatrix.getText().toString();
                phone = etPhone.getText().toString();
                email = etEmail.getText().toString();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child("Food Driver").child(userId);
                HashMap<String,Object> map = new HashMap<>();
                map.put("username",username);
                map.put("matric",matrix);
                map.put("phone",phone);
                map.put("email",email);
                myRef.updateChildren(map);
                Toast.makeText(Driver_Setting.this, "Your profile have been updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Driver_Setting.this, driver_mainmenu.class));

            }


        });

    }
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");
        pd.show();

        if(imageUrl != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUrl));
            uploadTask = fileReference.putFile(imageUrl);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task <UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task <Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("Food Driver").child(userId);
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("profilePic",mUri);
                        reference.updateChildren(map);
                        pd.dismiss();
                        Toast.makeText(Driver_Setting.this, "Profile picture upload successful!Exit and get back this page to view your latest profile picture.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Driver_Setting.this, "Failed to upload profile picture!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Driver_Setting.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            Toast.makeText(Driver_Setting.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            imageUrl = data.getData();
            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(Driver_Setting.this, "Upload is in progress.", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
    }

}
