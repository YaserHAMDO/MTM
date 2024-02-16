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
import com.example.mtm.model.SubInternetModel;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class SubInternetAdapter extends RecyclerView.Adapter<SubInternetAdapter.ViewHolder> {

    private List<SubInternetModel> items;
    private Context mContext;
    private List<SubInternetModel> mItems;
    private final OnItemClickListener listener;

    public SubInternetAdapter(Context context, List<SubInternetModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_sub_internet_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubInternetModel itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getImageStoragePath()).into(holder.journalistImageView);

        holder.mediaNameTextView.setText(itemData.getMediaName());
        holder.journalistNameTextView.setText(itemData.getJournalistName());
        holder.dateTextView.setText(itemData.getDate());

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(itemData.getShareLink());
        });

        holder.shareCardView.setOnClickListener(view -> {
            MyUtils.shareLink(itemData.getShareLink(), mContext);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView journalistImageView;
        TextView mediaNameTextView;
        TextView journalistNameTextView;
        TextView dateTextView;
        CardView shareCardView;

        ViewHolder(View itemView) {
            super(itemView);
            journalistImageView = itemView.findViewById(R.id.journalistImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            mediaNameTextView = itemView.findViewById(R.id.mediaNameTextView);
            journalistNameTextView = itemView.findViewById(R.id.journalistNameTextView);
            shareCardView = itemView.findViewById(R.id.shareCardView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String mediaPath);
    }

    // Method to add more items to the existing list
    public void addItems(List<SubInternetModel> newItems) {
        int startPosition = mItems.size(); // Get the current size of the list
        mItems.addAll(newItems); // Add new items to the existing list
        notifyItemRangeInserted(startPosition, newItems.size()); // Notify adapter about the newly added items
    }
}
