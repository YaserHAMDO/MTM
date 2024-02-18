package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.model.InternetSubListModel;

import java.util.List;

public class InternetSubListAdapter extends RecyclerView.Adapter<InternetSubListAdapter.ViewHolder> {

    private Context mContext;
    private List<InternetSubListModel> mItems;
    private final OnItemClickListener listener;
    private boolean orange;
    public InternetSubListAdapter(Context context, List<InternetSubListModel> items, boolean orange, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.orange = orange;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_internet_sub_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InternetSubListModel itemData = mItems.get(position);

        holder.nameTextView.setText(itemData.getName());
        holder.countTextView.setText(String.valueOf(itemData.getCount()));



        holder.itemView.setOnClickListener(view -> {
            listener.onItemClickInternetSubList(itemData.getMenuId(), itemData.getSubMenuId(), itemData.getCount());

            holder.progressBar.setVisibility(View.VISIBLE);

            new android.os.Handler().postDelayed(
                    () -> holder.progressBar.setVisibility(View.GONE),
                    1000
            );
        });

        if (orange) {
            holder.view.setBackgroundResource(R.drawable.baseline_play_arrow_24_2);
        } else {
            holder.view.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView countTextView;
        View view;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            countTextView = itemView.findViewById(R.id.countTextView);
            view = itemView.findViewById(R.id.view);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }



    public interface OnItemClickListener {
        void onItemClickInternetSubList(String menuId, String subMenuId, int count);
    }
}