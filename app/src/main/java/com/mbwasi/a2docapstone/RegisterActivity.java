package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName;
    EditText email;
    EditText password;
    EditText repeatPassword;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.buttonSignup);

        fullName = findViewById(R.id.nameET);
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.password1ET);
        repeatPassword = findViewById(R.id.password2ET);
        phone = findViewById(R.id.phoneET);

    }

    public void registerPressed(View view) {
        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.i("RegisterActivity", response.toJSONObject().toString());

                        JSONObject jsonObject = response.toJSONObject().getJSONObject("success");
                        String token =jsonObject.getString("token");
                        Log.i("RegisterActivity", "Token: "+token);

                        if(TokenUtils.storeLoginToken(token,getApplicationContext())){
                            //If token was successfully stored continue to main page
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

                    Toasty.error(getApplicationContext(), "Invalid Registration", Toast.LENGTH_LONG, true).show();

                }
                //Any other HTTP status
                else{

                    Toasty.error(getApplicationContext(), "Registration Error", Toast.LENGTH_LONG, true).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                Toasty.error(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG, true).show();
                Log.e("RegisterActivity", error.toString());
            }
        });

        JSONObject json;
        try {
            json = new JSONObject();
            json.put("name", fullName.getText());
            json.put("email", email.getText());
            json.put("password", password.getText());
            json.put("c_password", repeatPassword.getText());
            json.put("phone", phone.getText());

        } catch (JSONException ignore) {
            return;
        }
        request.post("http://2doo.ca/api/user/register", json);

//        Intent intent= new Intent(this, LoginActivity.class);
//        startActivity(intent);


    }
}
