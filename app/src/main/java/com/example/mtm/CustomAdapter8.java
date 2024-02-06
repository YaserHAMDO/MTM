package com.example.mtm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter8 extends RecyclerView.Adapter<CustomAdapter8.ViewHolder> {

    private Context mContext;
    private List<ItemData4> mItems;
    private final OnItemClickListener listener;
    public CustomAdapter8(Context context, List<ItemData4> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item6, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData4 itemData = mItems.get(position);


        holder.nameTextView.setText(itemData.getName());
        holder.countTextView.setText(itemData.getCount() + "");



        holder.itemView.setOnClickListener(view -> {
//            listener.onItemClick("39096", "113582");
            listener.onItemClick(itemData.getMenuId(), itemData.getSubMenuId());
        });


        // Set progress bar visibility
//        holder.progressView.setVisibility(itemData.isProgressBarVisible() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView countTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            countTextView = itemView.findViewById(R.id.countTextView);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(String menuId, String subMenuId);
    }
}