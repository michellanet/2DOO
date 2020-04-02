package com.mbwasi.a2docapstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    List<Category> categoriesList;
    private Context newContext;

    public RecycleViewAdapter(List<Category> categoriesList, Context newContext) {
       this.categoriesList = categoriesList;
        this.newContext = newContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_catlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(newContext).asBitmap().load(categoriesList.get(position).getImageURL()).into(holder.picture);




        holder.categoryName.setText(categoriesList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView picture;
        TextView categoryName;
        RelativeLayout catLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            picture = itemView.findViewById(R.id.Pic);
            categoryName = itemView.findViewById(R.id.foodType);
            catLayout = itemView.findViewById(R.id.category_parent);

        }
    }

}
