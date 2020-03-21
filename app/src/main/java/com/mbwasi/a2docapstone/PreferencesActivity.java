package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    PreferencesModel model = new PreferencesModel();




    private TextView textView;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private SharedPreferences preferences;

    public static final String PROGRESS = "SEEKBAR";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        textView = findViewById(R.id.tv);
        progressBar = findViewById(R.id.pb);
        seekBar = findViewById(R.id.sb);

        preferences = getSharedPreferences(" ", MODE_PRIVATE);
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


        Toast.makeText(this,"Preferences Saved!", Toast.LENGTH_SHORT).show();

    }
}

