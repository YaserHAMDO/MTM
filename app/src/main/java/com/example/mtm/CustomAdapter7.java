package com.example.mtm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class CustomAdapter7 extends RecyclerView.Adapter<CustomAdapter7.ViewHolder> implements  CustomAdapter8.OnItemClickListener{

    private Context mContext;
    private List<ItemData5> mItems;
    private final CustomAdapter8.OnItemClickListener listener;
    public CustomAdapter7(Context context, List<ItemData5> items, CustomAdapter8.OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item5, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData5 itemData = mItems.get(position);


        holder.nameTextView.setText(itemData.getName());
        holder.countTextView.setText(itemData.getCount() + "");



        holder.itemView.setOnClickListener(view -> {
//            listener.onItemClick("itemData.getImageStoragePath()");
//            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(holder.recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });

        boolean orange;

        orange = position % 2 == 0;

        if (orange) {
            holder.view.setBackgroundResource(R.drawable.orange_circule);
        }
        else {
            holder.view.setBackgroundResource(R.drawable.blue_circule);
        }



        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.recyclerView.setAdapter(new CustomAdapter8(mContext, itemData.getItemData4(), orange, listener));


        // Set progress bar visibility
//        holder.progressView.setVisibility(itemData.isProgressBarVisible() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemClick(String menuId, String subMenuId) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView countTextView;
        RecyclerView recyclerView;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            countTextView = itemView.findViewById(R.id.countTextView);
            recyclerView = itemView.findViewById(R.id.recyclerViewxxx);
            view = itemView.findViewById(R.id.view);
        }
    }
}