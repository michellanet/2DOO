

package com.mbwasi.a2docapstone;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mbwasi.a2docapstone.ReservationFragment.OnListFragmentInteractionListener;
import com.mbwasi.a2docapstone.entity.ReservationProvider;
import com.mbwasi.a2docapstone.entity.ReservationProvider.ReservationItem;

import java.util.List;
import java.util.Random;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ReservationItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReservationRecyclerViewAdapter extends RecyclerView.Adapter<MyReservationRecyclerViewAdapter.ViewHolder> {

    private final List<ReservationItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    int pos = 0;

    public MyReservationRecyclerViewAdapter(List<ReservationItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        pos = position;
        holder.mItem = mValues.get(position);
        holder.partySize.setText("Table Size: " + mValues.get(position).tableSize);
        holder.date.setText("Reserved Date: " + mValues.get(position).date);
        holder.time.setText("Reserved Time: " + mValues.get(position).time);
        holder.confirmationNo.setText("Confirmation No: " + mValues.get(position).confirmationNo);
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //mValues.remove(position);
                //mValues.remove(position);

                notifyItemRemoved(pos);
                Toast.makeText(view.getContext(), "Reservation canceled", Toast.LENGTH_SHORT).show();
                ReservationProvider.ITEMS.remove(position);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView partySize;
        public final TextView date;
        public final TextView time;
        public final TextView confirmationNo;
        public final Button cancelButton;
        public ReservationItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            partySize = (TextView) view.findViewById(R.id.partySize);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            confirmationNo = (TextView) view.findViewById(R.id.confirmationNo);
            cancelButton = (Button) view.findViewById(R.id.cancel_button);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + confirmationNo.getText() + "'";
        }
    }
}




//package com.mbwasi.a2docapstone;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mbwasi.a2docapstone.ReservationFragment.OnListFragmentInteractionListener;
//import com.mbwasi.a2docapstone.entity.ReservationProvider.ReservationItem;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * {@link RecyclerView.Adapter} that can display a {@link ReservationItem} and makes a call to the
// * specified {@link OnListFragmentInteractionListener}.
// * TODO: Replace the implementation with code for your data type.
// */
//public class MyReservationRecyclerViewAdapter extends RecyclerView.Adapter<MyReservationRecyclerViewAdapter.ViewHolder> {
//
//    private final List<ReservationItem> mValues;
//    private final OnListFragmentInteractionListener mListener;
//
//    public MyReservationRecyclerViewAdapter(List<ReservationItem> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_reservation, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        Random random = new Random();
//        int randomNumber = random.nextInt(999-100) + 100;
//        holder.mItem = mValues.get(position);
//        holder.partySize.setText("Table Size: " + mValues.get(position).tableSize);
//        holder.date.setText("Reserved Date: " + mValues.get(position).date);
//        holder.time.setText("Reserved Time: " + mValues.get(position).time);
//        holder.confirmationNo.setText("Confirmation No: " + randomNumber +"");
//        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                //mValues.remove(position);
//                Toast.makeText(view.getContext(), "Reservation " +(position+1)+ " is canceled", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView partySize;
//        public final TextView date;
//        public final TextView time;
//        public final TextView confirmationNo;
//        public final Button cancelButton;
//        public ReservationItem mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            partySize = (TextView) view.findViewById(R.id.partySize);
//            date = (TextView) view.findViewById(R.id.date);
//            time = (TextView) view.findViewById(R.id.time);
//            confirmationNo = (TextView) view.findViewById(R.id.confirmationNo);
//            cancelButton = (Button) view.findViewById(R.id.cancel_button);
//
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + confirmationNo.getText() + "'";
//        }
//    }
//}
