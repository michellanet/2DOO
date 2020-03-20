package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CategoryActivity extends BaseActivity {

    private ArrayList<String> catNames = new ArrayList<>();
    private ArrayList<String> catPics = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initImages();
    }

    private void initImages(){
        catPics.add("https://cdn.countryflags.com/thumbs/india/flag-800.png");
        catNames.add("Indian");

        catPics.add("https://cdn.countryflags.com/thumbs/china/flag-800.png");
        catNames.add("Chinese");

        catPics.add("https://cdn.countryflags.com/thumbs/vietnam/flag-800.png");
        catNames.add("Vietnamese");

        catPics.add("https://cdn.countryflags.com/thumbs/south-africa/flag-800.png");
        catNames.add("African");


        catPics.add("https://cdn.countryflags.com/thumbs/thailand/flag-800.png");
        catNames.add("Thai");

        catPics.add("https://cdn.countryflags.com/thumbs/mexico/flag-800.png");
        catNames.add("Mexican");

        catPics.add("https://cdn.countryflags.com/thumbs/japan/flag-800.png");
        catNames.add("Japanese");

        catPics.add("https://cdn.countryflags.com/thumbs/brazil/flag-800.png");
        catNames.add("Brazilian BBQ");

        initRecycler();
    }

    private void initRecycler(){
        RecyclerView rv = findViewById(R.id.rv);
        RecycleViewAdapter rva = new RecycleViewAdapter(catNames, catPics, this);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
