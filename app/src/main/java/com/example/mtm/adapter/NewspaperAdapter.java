package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtm.R;
import com.example.mtm.model.NewspaperModel;

import java.util.List;

public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {

    private Context mContext;
    private List<NewspaperModel> mItems;
    private final OnItemClickListener listener;
    public NewspaperAdapter(Context context, List<NewspaperModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_newspaper_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewspaperModel itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getImageUrl()).into(holder.mediaImageView);

        holder.textView.setText(itemData.getName());


        holder.mediaImageView.setOnClickListener(view -> {
            listener.onItemClick(itemData.getMediaPath(), itemData.getPageFile(), itemData.getGno());
        });

        // Set progress bar visibility
//        holder.progressView.setVisibility(itemData.isProgressBarVisible() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mediaImageView;
        TextView textView;
        ProgressBar progressView;

        ViewHolder(View itemView) {
            super(itemView);
            mediaImageView = itemView.findViewById(R.id.xxxx);
            textView = itemView.findViewById(R.id.textView);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(String mediaPath, String pageFile, String gno);
    }
}
