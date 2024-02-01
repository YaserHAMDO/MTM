package com.example.mtm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter4 extends RecyclerView.Adapter<CustomAdapter4.ViewHolder> {

    private final Context context;
    private final List<String> dataList;
    private int selectedItem = 0;
    private final OnItemClickListener listener;

    public CustomAdapter4(Context context, List<String> dataList, OnItemClickListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        String item = dataList.get(position);


        Glide.with(context).load(item).into(holder.imageView);



        // Change text color if the item is selected
//        if (selectedItem == pos) {
//            holder.textView.setTextColor(context.getColor(R.color.black));
//            holder.view.setVisibility(View.VISIBLE);
//        } else {
//            holder.textView.setTextColor(context.getColor(R.color.color1));
//            holder.view.setVisibility(View.INVISIBLE);
//        }

        // Set item click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the view
                if (selectedItem == pos) {
                    selectedItem = -1; // Deselect item
                } else {
                    selectedItem = pos; // Select item
                }

                // Notify adapter that data has changed
                notifyDataSetChanged();
//                listener.onItemClick(holder.type);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.festivalsImageView_newHubFragment);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String position);
    }
}
