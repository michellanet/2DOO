package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    Toast.makeText(CategoryActivity.this, "Invalid Token", Toast.LENGTH_LONG).show();
                }
                //Any other HTTP status
                else{
                    Log.e(TAG, "Response code:" + response.code);
                    Toast.makeText(CategoryActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        //IF request fails
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                Toast.makeText(CategoryActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
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

        // initImages();
    }

//    private void initImages(){
////        catPics.add("https://cdn.countryflags.com/thumbs/india/flag-800.png");
////        catNames.add("Indian");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/china/flag-800.png");
////        catNames.add("Chinese");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/vietnam/flag-800.png");
////        catNames.add("Vietnamese");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/south-africa/flag-800.png");
////        catNames.add("African");
////
////
////        catPics.add("https://cdn.countryflags.com/thumbs/thailand/flag-800.png");
////        catNames.add("Thai");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/mexico/flag-800.png");
////        catNames.add("Mexican");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/japan/flag-800.png");
////        catNames.add("Japanese");
////
////        catPics.add("https://cdn.countryflags.com/thumbs/brazil/flag-800.png");
////        catNames.add("Brazilian BBQ");
////
////        initRecycler();
////    }

    private void initRecycler(){
        RecyclerView rv = findViewById(R.id.rv);

        //Pass categoriesList to RecyclerView adapter instead of catnames& CatPics
        //get cat image url by categoryList[i].getImageURL()
        //name by categoryLust[i].getName()
        RecycleViewAdapter rva = new RecycleViewAdapter(catNames, catPics, this);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
