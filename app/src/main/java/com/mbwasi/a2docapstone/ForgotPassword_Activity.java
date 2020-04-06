package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword_Activity extends AppCompatActivity {

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);
    }

    public void resetClicked(View view) {

        email = findViewById(R.id.emailResetET);
        String email1 = email.getText().toString();

        if(email1.isEmpty()){

            new AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("CannDeot Leave E-Mail Empty!")


                    .setNegativeButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }else {


            new AlertDialog.Builder(this)
                    .setTitle("Request Password Change")
                    .setMessage("Are you sure you want to reset your password??")


                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(getApplicationContext(), "Check Your E-Mail for Password Reset", Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



        }

    }

    public void registerPressed(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void loginRedirectPressed(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
