package com.example.mtm.adapter;

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
import com.example.mtm.R;
import com.example.mtm.activity.ImageShowActivity;
import com.example.mtm.activity.VideoShowActivity;
import com.example.mtm.model.MediaAgendaModel;

import java.util.List;

public class MediaAgendaBodyAdapter extends RecyclerView.Adapter<MediaAgendaBodyAdapter.ViewHolder> {

    private final Context context;
    private final List<MediaAgendaModel> dataList;
    private int selectedItem = 0;


    public MediaAgendaBodyAdapter(Context context, List<MediaAgendaModel> dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_media_agenda_body, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        MediaAgendaModel item = dataList.get(pos);
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

            Intent intent = new Intent(context, VideoShowActivity.class);
            intent.putExtra("videoUrl", item.getVideoUrl());
            context.startActivity(intent);

        });

        holder.readMoreLinearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ImageShowActivity.class);
            intent.putExtra("imageUrl", item.getMagazineImageUrl());
            context.startActivity(intent);
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
