package com.medyatakip.app.adapter;

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
import com.medyatakip.app.R;
import com.medyatakip.app.model.MagazineModel;


import java.util.List;
public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.ViewHolder> {

    private Context mContext;
    private List<MagazineModel> mItems;
    private final OnItemClickListener listener;
    public MagazineAdapter(Context context, List<MagazineModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_magazine_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MagazineModel itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getImageUrl()).into(holder.mediaImageView);

        holder.textView.setText(itemData.getName());


        holder.mediaImageView.setOnClickListener(view -> {
            listener.onItemClick(itemData.getMediaPath(), itemData.getPageFile(), itemData.getGno(), itemData.getCount(), position);
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
        void onItemClick(String mediaPath, String pageFile, String gno, int count, int position);
    }
}
