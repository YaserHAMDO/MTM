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
import com.example.mtm.model.ColumnistModel;
import com.example.mtm.util.Constants;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class ColumnistAdapter extends RecyclerView.Adapter<ColumnistAdapter.ViewHolder> {

    private Context mContext;
    private List<ColumnistModel> mItems;
    private final OnItemClickListener listener;
    public ColumnistAdapter(Context context, List<ColumnistModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_columnist_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColumnistModel itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getJournalistImageUrl()).into(holder.journalistImageView);

        holder.mediaNameTextView.setText(itemData.getMediaName());
        holder.journalistNameTextView.setText(itemData.getJournalistName());
        holder.dateTextView.setText(itemData.getDate());
        holder.titleTextView.setText(itemData.getTitle());

        holder.positionTextView.setText("/sf." + (position + 1));


        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(position);
        });

        holder.shareCardView.setOnClickListener(view -> {

            MyUtils.shareLink(itemData.getGnoHash(), mContext);

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
        TextView positionTextView;
        TextView dateTextView;
        CardView shareCardView;


        ViewHolder(View itemView) {
            super(itemView);
            journalistImageView = itemView.findViewById(R.id.journalistImageView);
            mediaNameTextView = itemView.findViewById(R.id.mediaNameTextView);
            journalistNameTextView = itemView.findViewById(R.id.journalistNameTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            shareCardView = itemView.findViewById(R.id.shareCardView);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
