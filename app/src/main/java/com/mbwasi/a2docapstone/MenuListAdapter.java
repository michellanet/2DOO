package com.mbwasi.a2docapstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MenuListAdapter extends RecyclerView.Adapter {

    public String[] names = new String[] {
        "Lobster Super Bowl",
        "Seafood Super Bowl",
        "Fresh Hokkiga Congee",
        "Salmon Congee",
        "Chicken & Duck Congee"
    };

    public String[] prices = new String[] {
            "$17.5",
            "$29.99",
            "$8.5",
            "$10.5",
            "$28.5",
    };


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_menu_list_item, parent, false);
        return new ListViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHoler) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    private class ListViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtItemName;
        private TextView txtItemPrice;

        public ListViewHoler(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            txtItemName.setText(names[position]);
            txtItemPrice.setText(prices[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
