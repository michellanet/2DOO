package com.mbwasi.a2docapstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import pk.codebase.requests.FormData;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpHeaders;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class ProfileActivity extends BaseActivity {

    EditText fullName;
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

        fullName = (EditText)findViewById(R.id.fName);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.address);
        image = (CircleImageView)findViewById(R.id.Pic);


        fullName.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);


        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.i(TAG, "JSONObject: "+ response.toJSONObject().toString());


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

                    Log.i(TAG, "Name: " + currentUser.getAvatar());

                    fullName.setText(currentUser.getName());
                    email.setText(currentUser.getEmail());
                    phone.setText(currentUser.getPhone());

                    Log.i(TAG,"http://2doo.ca/storage/avatars/"+currentUser.getAvatar());
                    Picasso.get().load("http://2doo.ca/storage/avatars/"+currentUser.getAvatar()).into(image);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }
                }

                else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {

                    Toasty.error(getApplicationContext(), "Invalid Token: Reload Page", Toast.LENGTH_LONG, true).show();
                }
                //Any other HTTP status
                else{
                    Log.e(TAG, "Response code:" + response.code);

                    Toasty.error(getApplicationContext(), "Network Error: Reload Page", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {

                Toasty.error(getApplicationContext(), "Error: Reload Page", Toast.LENGTH_LONG, true).show();
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
        request.post("http://2doo.ca/api/user/details", json,headers);

    }

    public void editProfile(View view) {

        fullName.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);

    }

    public void saveProfile(View view) throws IOException {

        fullName.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();


        File f = new File(context.getCacheDir(), "filename");

        f.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();


//        byte[] bitmapdata = bos.toByteArray();
//
//        FileOutputStream fos = new FileOutputStream(f);
//
//        fos.write(bitmapdata);






        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
//                        Log.e("ProfileActivity", response.toJSONObject().toString());

                        JSONObject jsonObject = response.toJSONObject().getJSONObject("success");
                        String token =jsonObject.getString("token");
                        Log.e("ProfileActivity", "Token: "+token);

                        if(TokenUtils.storeLoginToken(token,getApplicationContext())){
                            //If token was succesfully stored continue to main page
                            Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //UNATHORIZED, wront user/pass or doesnt exist.
                else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {

                    Toasty.error(getApplicationContext(), "Invalid Try Again", Toast.LENGTH_LONG, true).show();

                }
                //Any other HTTP status
                else{

                    Toasty.error(getApplicationContext(), "Invalid Try Again", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {

                Toasty.error(getApplicationContext(), "Update Failed Try Again", Toast.LENGTH_LONG, true).show();
                Log.e("RegisterActivity", error.toString());
            }
        });

        JSONObject json;
        try {
            json = new JSONObject();
            json.put("name", fullName.getText());
            json.put("email", email.getText());
            json.put("phone", phone.getText());
            json.put("avatar",  bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos));

        } catch (JSONException ignore) {
            return;
        }
        request.post("http://2doo.ca/api/user/update", json);

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

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            Log.i(TAG, "getRealPathFromURI: " + cursor.getString(idx));
            return cursor.getString(idx);
        }
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        byte[] data = FileUtils.readFileToByteArray(file);
        return data;

    }

    private void saveAvatarImageToInternalStorage( String sourcePath) {

        try {
            FileOutputStream fos  = new FileOutputStream(getFilesDir() + "/avatar.jpg",false);
            File file = new File(sourcePath);

            byte[] bytes = getBytesFromFile(file);

            fos.write(bytes);
            fos.close();

          //  Toast.makeText(getApplicationContext(),"File saved in :"+ getFilesDir() + "/"+name,Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Log.i(TAG,"Path:" + data.getData().getPath());

            Uri selectedImageURI = data.getData();

            //Begin posting profile to server
            //Turn URI into path

            image.setImageURI(data.getData());

            String actualPath = getRealPathFromURI(selectedImageURI);
            File imageFile = new File(actualPath);
            saveAvatarImageToInternalStorage(actualPath);

            HttpRequest request = new HttpRequest();
            request.setOnResponseListener(new HttpRequest.OnResponseListener() {
                @Override
                public void onResponse(HttpResponse response) {
                    if (response.code == HttpResponse.HTTP_OK) {
                        Log.d(TAG, "onResponse: " + response.text);
                    }
                }
            });
            request.setOnErrorListener(new HttpRequest.OnErrorListener() {
                @Override
                public void onError(HttpError error) {
                    Log.e(TAG, "onError: ",error );
                }
            });

            String token = TokenUtils.getLoginToken(getApplicationContext());

            if(token!=null){
                String bearerToken = "Bearer " + token;
                Log.d(TAG, bearerToken);
                HttpHeaders headers = new HttpHeaders("Authorization", bearerToken);

                FormData formData = new FormData();
                //TODO:: get actual values from profile activity
                formData.put("name","Romano");
                formData.put("email","mbwasiroman@gmail.com");
                formData.put("phone","12345678");
                formData.put("avatar", imageFile);
                // Localhost test site     request.post("http://10.0.2.2/uploadtest/upload.php", formData);
                request.post("http://2doo.ca/api/user/update", formData,headers);

            }else{
                Toasty.error(getApplicationContext(), "Invalid Token!", Toast.LENGTH_LONG, true).show();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
            //End posting profile

        }
    }
    }
