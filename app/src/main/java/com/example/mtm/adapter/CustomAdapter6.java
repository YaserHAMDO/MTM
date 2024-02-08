package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtm.R;
import com.example.mtm.model.ItemData3;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class CustomAdapter6 extends RecyclerView.Adapter<CustomAdapter6.ViewHolder> {

    private Context mContext;
    private List<ItemData3> mItems;
    private final OnItemClickListener listener;
    public CustomAdapter6(Context context, List<ItemData3> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item4, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData3 itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getJournalistImageUrl()).into(holder.journalistImageView);

        holder.mediaNameTextView.setText(itemData.getMediaName());
        holder.journalistNameTextView.setText(itemData.getJournalistName());
        holder.dateTextView.setText(itemData.getDate());
        holder.titleTextView.setText(itemData.getTitle());


        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(itemData.getImageStoragePath());
        });

        holder.shareCardView.setOnClickListener(view -> {

            MyUtils.shareLink("Test link", mContext);

        });

        // Set progress bar visibility
//        holder.progressView.setVisibility(itemData.isProgressBarVisible() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView journalistImageView;
        TextView mediaNameTextView;
        TextView journalistNameTextView;
        TextView titleTextView;
        TextView dateTextView;
        CardView shareCardView;


        ViewHolder(View itemView) {
            super(itemView);
            journalistImageView = itemView.findViewById(R.id.journalistImageView);
            mediaNameTextView = itemView.findViewById(R.id.mediaNameTextView);
            journalistNameTextView = itemView.findViewById(R.id.journalistNameTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            shareCardView = itemView.findViewById(R.id.shareCardView);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(String mediaPath);
    }
}