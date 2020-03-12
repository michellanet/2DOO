package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class ReservationActivity extends BaseActivity {

    private TextView dateInput;
    private Spinner partySize;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView timeInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        dateInput = (TextView) findViewById(R.id.date);
        partySize = (Spinner) findViewById(R.id.partySize);
        timeInput = (TextView) findViewById(R.id.time);
    }

    public void calPopup(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                ReservationActivity.this,
                R.style.DialogTheme,
                mDateSetListener,
                year,month,day);
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateInput.setText(date);
            }
        };
    }

    public void timePopup(View view){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        final int meridian = cal.get(Calendar.AM_PM);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                ReservationActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            //private String am_pm;

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //if (meridian == Calendar.AM)
                //am_pm = "AM";
                //else if (meridian == Calendar.PM)
                //am_pm = "PM";
                timeInput.setText(hourOfDay + " : " + minute + " Hours");
            }
        }, hour, minute,android.text.format.DateFormat.is24HourFormat(ReservationActivity.this)
        );
        timePickerDialog.show();
    }


}
