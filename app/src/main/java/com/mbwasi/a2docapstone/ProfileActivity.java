package com.mbwasi.a2docapstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends BaseActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText address;

    ImageView image;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstName = (EditText)findViewById(R.id.fName);
        lastName = (EditText)findViewById(R.id.lName);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.address);
        image = (ImageView)findViewById(R.id.Pic);


        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);


    }

    public void editProfile(View view) {

        firstName.setEnabled(true);
        lastName.setEnabled(true);
        email.setEnabled(true);
        address.setEnabled(true);

    }

    public void saveProfile(View view) {

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);


    }



    public void editPic(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_CODE);
            }else{
                pickImageFromGallery();
            }




        }else{

            pickImageFromGallery();

        }

    }



    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    pickImageFromGallery();
                }else{

                    Toasty.error(getApplicationContext(), "Permission Denied!", Toast.LENGTH_LONG, true).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            image.setImageURI(data.getData());
        }
    }
    }

