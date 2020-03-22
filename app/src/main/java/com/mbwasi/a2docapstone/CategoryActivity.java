package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpHeaders;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class CategoryActivity extends BaseActivity {

    public static final String TAG = "CategoryActivity";
    private ArrayList<String> catNames = new ArrayList<>();
    private ArrayList<String> catPics = new ArrayList<>();

    List<Category> categoriesList=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //HTTP POST request
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {

                    try {
                        Log.e(TAG, "JSONObject: "+ response.toJSONObject().toString());
                        JSONObject successObject = response.toJSONObject().getJSONObject("success");
                        JSONArray jsonArrayOfCategories = successObject.getJSONArray("data");

                        Log.e(TAG, "Data: "+jsonArrayOfCategories.toString());
                        Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
                        categoriesList = new Gson().fromJson(jsonArrayOfCategories.toString(), listType);
                        initRecycler();
                        //TODO: Take list categoriesList and use it in RecyclerAdapter to populate the RecyclerView
                        //

//                        for (Category cat: categoriesList ) {
//                            Log.i(CATEGORY_ACTIVITY, cat.getName());
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }
                }

                else if (response.code == HttpResponse.HTTP_UNAUTHORIZED) {

                    Toasty.error(getApplicationContext(), "Invalid Token", Toast.LENGTH_LONG, true).show();
                }
                //Any other HTTP status
                else{
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
        request.post("http://2doo.ca/api/category/list", json,headers);

    }



    private void initRecycler(){
        RecyclerView rv = findViewById(R.id.rv);

        //Pass categoriesList to RecyclerView adapter
        //get cat image url by categoriesList[i].getImageURL()
        //name by categoriesList[i].getName()
        RecycleViewAdapter rva = new RecycleViewAdapter(categoriesList, this);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    //when save is clicked
    public void saveCat(View view) {
    }
}
