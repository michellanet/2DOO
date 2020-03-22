package com.mbwasi.a2docapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.mbwasi.a2docapstone.entity.ReservationProvider;

public class ReservationHistoryListActivity extends BaseActivity implements ReservationFragment.OnListFragmentInteractionListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
    }

    @Override
    public void onListFragmentInteraction(ReservationProvider.ReservationItem item) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
    }
}
