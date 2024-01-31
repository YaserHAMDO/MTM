package com.example.mtm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder> {

    private final Context context;
    private final List<Models> dataList;
    private int selectedItem = 0;


    public CustomAdapter3(Context context, List<Models> dataList) {
        this.context = context;
        this.dataList = dataList;

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
        Models item = dataList.get(pos);
        holder.title.setText(item.getTitle());
        holder.body.setText(item.getBody());


        Glide.with(context).load(item.getImageUrl()).into(holder.imageView);
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

            }
        });

        if (item.getVideoUrl() != null && !item.getVideoUrl().equals("")) {
            holder.watchLinearLayout.setVisibility(View.VISIBLE);
        }
        else {
            holder.watchLinearLayout.setVisibility(View.GONE);
        }


        holder.watchLinearLayout.setOnClickListener(view -> {

            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("videoUrl", item.getVideoUrl());
            context.startActivity(intent);

        });

        holder.readMoreLinearLayout.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView body;
        ImageView imageView;
        LinearLayout watchLinearLayout;
        LinearLayout readMoreLinearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            imageView = itemView.findViewById(R.id.festivalsImageView_newHubFragment);
            watchLinearLayout = itemView.findViewById(R.id.watchLinearLayout);
            readMoreLinearLayout = itemView.findViewById(R.id.readMoreLinearLayout);

        }
    }

}
