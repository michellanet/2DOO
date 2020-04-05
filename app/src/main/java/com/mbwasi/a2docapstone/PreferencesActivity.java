package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class PreferencesActivity extends AppCompatActivity {

    PreferencesModel model = new PreferencesModel();




    private TextView textView;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private SharedPreferences preferences;
    private Button logoutButton;

    public static final String PROGRESS = "SEEKBAR";
    public static final String DISTANCE = "distancePrefs";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        logoutButton = findViewById(R.id.logoutButton);

        textView = findViewById(R.id.tv);
        progressBar = findViewById(R.id.pb);
        seekBar = findViewById(R.id.sb);

        preferences = getSharedPreferences(DISTANCE, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        seekBar.setProgress(preferences.getInt(PROGRESS, 0));


        textView.setText(seekBar.getProgress() + " KM");
        progressBar.setProgress(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText(progress + " KM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                editor.putInt(PROGRESS, seekBar.getProgress());
                editor.commit();

            }
        });
    }




    public void saveDistance(View view) {

        model.setDistance(seekBar.getProgress());

        //Intent to send model instance to Main Activity

        Toasty.success(getApplicationContext(), "Preferences Saved!", Toast.LENGTH_LONG, true).show();

    }

    //Logout Button
    public void logoutPressed(View view) {

        SharedPreferences preferences = getSharedPreferences("2DOO_PREFS", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();


        Intent intent  = new Intent(this, LoginActivity.class);
        startActivity(intent);



    }
}

