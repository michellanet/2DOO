package com.mbwasi.a2docapstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mbwasi.a2docapstone.entity.ReservationProvider;

import java.util.Calendar;


public class ReservationActivity extends BaseActivity {

    private TextView dateInput;
    private Spinner partySize;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView timeInput;
    Calendar cal = Calendar.getInstance();
    int year, month, day, hour, minute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        dateInput = (TextView) findViewById(R.id.date);
        partySize = (Spinner) findViewById(R.id.partySize);
        timeInput = (TextView) findViewById(R.id.time);

    }

    public void calPopup(View view){

// Process to get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(ReservationActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        dateInput.setText( (monthOfYear + 1) + "/"
                                + dayOfMonth + "/" + year);

                    }
                }, year, month, day);
        dpd.show();
    }

    public void timePopup(View view){
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
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
                timeInput.setText(hourOfDay + " : " + minute);
            }
        }, hour, minute,android.text.format.DateFormat.is24HourFormat(ReservationActivity.this)
        );
        timePickerDialog.show();
    }
    public void makeReservation(View view) {
        if (!dateInput.getText().toString().matches("") && !timeInput.getText().toString().matches("")) {

            Toast.makeText(this, "Table for Party of: " + partySize.getSelectedItem().toString()
                    + "\n reserved Date: " + dateInput.getText()
                    + "\n reserved Time: " + timeInput.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ReservationHistoryListActivity.class);
            ReservationProvider.ReservationItem item = new ReservationProvider.ReservationItem(
                    partySize.getSelectedItem().toString(),
                    dateInput.getText().toString(),
                    timeInput.getText().toString() + " Hours",
                    partySize.getSelectedItem().toString() + "-" + dateInput.getText().toString() + "-" + timeInput.getText().toString());
            ReservationProvider.ITEMS.add(0, item);

        /*
        //save item as json to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("reservationPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ReservationProvider.ITEMS);
        editor.putString("reservationList", json);
        editor.apply();*/

//            Intent intent = new Intent(this, ReservationHistoryListActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please ensure all input fields are filled", Toast.LENGTH_SHORT).show();
        }
    }


}



//package com.mbwasi.a2docapstone;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.view.View;
//import android.widget.DatePicker;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
//
//public class ReservationActivity extends BaseActivity {
//
//    private TextView dateInput;
//    private Spinner partySize;
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//    private TextView timeInput;
//    Calendar cal = Calendar.getInstance();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reservation);
//        dateInput = (TextView) findViewById(R.id.date);
//        partySize = (Spinner) findViewById(R.id.partySize);
//        timeInput = (TextView) findViewById(R.id.time);
//
//    }
//
//    public void calPopup(View view){
//
//// Process to get Current Date
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        // Launch Date Picker Dialog
//        DatePickerDialog dpd = new DatePickerDialog(ReservationActivity.this,
//                new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//                        // Display Selected date in textbox
//                        dateInput.setText( (monthOfYear + 1) + "/"
//                                + dayOfMonth + "/" + year);
//
//                    }
//                }, year, month, day);
//        dpd.show();
//    }
//
//    public void timePopup(View view){
//        Calendar cal = Calendar.getInstance();
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);
//        final int meridian = cal.get(Calendar.AM_PM);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(
//                ReservationActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
//            //private String am_pm;
//
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                //if (meridian == Calendar.AM)
//                //am_pm = "AM";
//                //else if (meridian == Calendar.PM)
//                //am_pm = "PM";
//                timeInput.setText(hourOfDay + " : " + minute + " Hours");
//            }
//        }, hour, minute,android.text.format.DateFormat.is24HourFormat(ReservationActivity.this)
//        );
//        timePickerDialog.show();
//    }
//    public void makeReservation(View view) {
//        Toast.makeText(this, "Table for Party of: " + partySize.getSelectedItem().toString()
//                + "\n reserved Date: " + dateInput.getText()
//                + "\n reserved Time: " + timeInput.getText(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, ReservationHistoryListActivity.class);
//        startActivity(intent);
//    }
//
//
//}
