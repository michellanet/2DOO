package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {
                    Log.e("LoginActivity", response.toJSONObject().toString());
                    System.out.println(response.toJSONObject());

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LOGIN_TOKEN", "string value"); // Storing string
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                // There was an error, deal with it
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", error.toString());
            }
        });
        request.get("https://jsonplaceholder.typicode.com/todos/1");



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
