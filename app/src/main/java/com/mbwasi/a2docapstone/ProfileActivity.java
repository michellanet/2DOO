package com.mbwasi.a2docapstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpHeaders;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class ProfileActivity extends BaseActivity {
    EditText firstName;
    EditText email;
    EditText phone;
    ImageView image;

    public static final String TAG = "ProfileActivity";

    User currentUser = new User();

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstName = (EditText) findViewById(R.id.fName);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.address);
        image = (ImageView) findViewById(R.id.Pic);

        firstName.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);

        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.e(TAG, "JSONObject: " + response.toJSONObject().toString());

                        JSONObject successObject = response.toJSONObject().getJSONObject("success");

                        currentUser.setId(successObject.getInt("id"));
                        currentUser.setName(successObject.getString("name"));
                        currentUser.setEmail(successObject.getString("email"));
                        currentUser.setEmail_verified_at(successObject.getString("email_verified_at"));
                        currentUser.setRole(successObject.getInt("role"));
                        currentUser.setPhone(successObject.getString("phone"));
                        currentUser.setAvatar(successObject.getString("avatar"));
                        currentUser.setStatus(successObject.getInt("status"));
                        currentUser.setCreated_at(successObject.getString("created_at"));
                        currentUser.setUpdated_at(successObject.getString("updated_at"));
                        currentUser.setDeleted_at(successObject.getString("deleted_at"));

                        Log.e(TAG, "Name: " + currentUser.getAvatar());

                        firstName.setText(currentUser.getName());
                        email.setText(currentUser.getEmail());
                        phone.setText(currentUser.getPhone());

                        Picasso.get().load("http://2doo.ca/storage/avatars/" + currentUser.getAvatar()).into(image);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }
                } else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {

                    Toasty.error(getApplicationContext(), "Invalid Token", Toast.LENGTH_LONG, true).show();
                }
                //Any other HTTP status
                else {
                    Log.e(TAG, "Response code:" + response.code);

                    Toasty.error(getApplicationContext(), "Network Error", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {

                Toasty.error(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG, true).show();
                Log.e("LoginActivity", error.toString());
            }
        });

        JSONObject json;
        try {
            json = new JSONObject();
            json.put("perPage", "10");
        } catch (JSONException ignore) {
            return;
        }
        HttpHeaders headers = new HttpHeaders("Authorization", "Bearer " + TokenUtils.getLoginToken(getApplicationContext()));
        request.post("http://2doo.ca/api/user/details", json, headers);

    }

    public void editProfile(View view) {
        firstName.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);
    }

    public void saveProfile(View view) {
        firstName.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
    }

    public void editPic(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                pickImageFromGallery();
            }
        } else {
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
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
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

