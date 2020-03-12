package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginPressed(View view) {
//        HttpRequest request = new HttpRequest();
//        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
//            @Override
//            public void onResponse(HttpResponse response) {
//                if (response.code == HttpResponse.HTTP_OK) {
//                    Log.e("LoginActivity", response.toJSONObject().toString());
//                    System.out.println(response.toJSONObject());
//
//                    if(TokenUtils.storeLoginToken("Token string goes here",getApplicationContext())){
//                        //If token was succesfully stored continue to main page
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
//        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
//            @Override
//            public void onError(HttpError error) {
//                // There was an error, deal with it
//                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
//                Log.e("LoginActivity", error.toString());
//            }
//        });
//        request.get("http://2doo.ca/api/login");
//////////////
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.e("LoginActivity", response.toJSONObject().toString());

                        JSONObject jsonObject = response.toJSONObject().getJSONObject("success");
                        String token =jsonObject.getString("token");
                        Log.e("LoginActivity", "Token: "+token);
                        if(TokenUtils.storeLoginToken("Token string goes here",getApplicationContext())){
                            //If token was succesfully stored continue to main page
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                }
            }
        });
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", error.toString());
            }
        });

        //TODO: Get username and password from edit text
        JSONObject json;
        try {
            json = new JSONObject();
            json.put("email", "romanmbwasi@gmail.com");
            json.put("password", "password");
        } catch (JSONException ignore) {
            return;
        }
        request.post("http://2doo.ca/api/login", json);




    }


    public void registerPressed(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPressed(View view) {
        Intent intent = new Intent(this, ForgotPassword_Activity.class);
        startActivity(intent);
    }
}
