package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginPressed(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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
