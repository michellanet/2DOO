package com.mbwasi.a2docapstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {


    private static final String KEY_TV = "tv_key";
    private static final String KEY_PB = "pb_key";
    private static final String KEY_SB = "sb_key";



   private TextView textView;
   private ProgressBar progressBar;
   private SeekBar seekBar;
   int progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        textView = (TextView)findViewById(R.id.tv);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        seekBar = (SeekBar) findViewById(R.id.sb);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                progress1 = progress;
                textView.setText("" + progress + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        if (savedInstanceState != null){

            String SavedKM = savedInstanceState.getString(KEY_TV);
            textView.setText(SavedKM);
        }else{
            Toast.makeText(this, "new entry", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {

        saveInstanceState.putString(KEY_TV, textView.getText().toString());

        super.onSaveInstanceState(saveInstanceState);
    }



    public void saveDistance(View view) {
textView.setText(progress1 + " KM");
    }
}
