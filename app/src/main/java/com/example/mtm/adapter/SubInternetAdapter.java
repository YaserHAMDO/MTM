package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private boolean allList;
    private int index;

    ViewHolder holder1;

    public SubInternetAdapter(Context context, List<SubInternetModel> items, OnItemClickListener listener, boolean allList, int index) {
        mContext = context;
        mItems = items;
        this.listener = listener;
        this.allList = allList;
        this.index = index;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_sub_internet_activity, parent, false);
        return new ViewHolder(view);
    }

    public void setAllList(boolean allList) {
        this.allList = allList;
    }

    public ViewHolder getHolder(){
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder1 = holder;
        SubInternetModel itemData = mItems.get(position);

        // Load image using Glide
        Glide.with(mContext).load(itemData.getImageStoragePath()).into(holder.journalistImageView);

        holder.mediaNameTextView.setText(itemData.getMediaName());
        holder.journalistNameTextView.setText(itemData.getJournalistName());
        holder.dateTextView.setText(itemData.getDate());

        switch (index) {
            case 1:
                holder.positionTextView.setVisibility(View.VISIBLE);
                holder.positionTextView.setText("/sf." + (position + 1));
                break;

            case 3:
                holder.positionTextView.setVisibility(View.VISIBLE);
                holder.positionTextView.setText("(" + itemData.getProgramName() + ")");
                break;

        }




        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(itemData.getShareLink(), itemData.getShareLink2(), position);
        });

        holder.shareCardView.setOnClickListener(view -> {
            MyUtils.shareLink(itemData.getHariciShareLink(), mContext);
        });


        if (allList) {
            holder.frameLayout.setVisibility(View.GONE);
        }
        else {
            if (position == mItems.size() - 1) {
                holder.frameLayout.setVisibility(View.VISIBLE);
//                holder.button.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
            else {
                holder.frameLayout.setVisibility(View.GONE);
            }
        }



//        holder.button.setOnClickListener(view -> {
//            holder.button.setVisibility(View.INVISIBLE);
//            holder.progressBar.setVisibility(View.VISIBLE);
//            listener.onShowMore();
//        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

   public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView journalistImageView;
        TextView mediaNameTextView;
        TextView positionTextView;
        TextView journalistNameTextView;
        TextView dateTextView;
        CardView shareCardView;
        FrameLayout frameLayout;
        Button button;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            journalistImageView = itemView.findViewById(R.id.journalistImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            mediaNameTextView = itemView.findViewById(R.id.mediaNameTextView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            journalistNameTextView = itemView.findViewById(R.id.journalistNameTextView);
            shareCardView = itemView.findViewById(R.id.shareCardView);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            button = itemView.findViewById(R.id.button);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public FrameLayout getFrameLayout() {
            return frameLayout;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String mediaPath, String mediaPath2, int position);
        void onShowMore();
    }

    // Method to add more items to the existing list
    public void addItems(List<SubInternetModel> newItems) {
        int startPosition = mItems.size(); // Get the current size of the list
        mItems.addAll(newItems); // Add new items to the existing list
        notifyItemRangeInserted(startPosition, newItems.size()); // Notify adapter about the newly added items
    }
}
