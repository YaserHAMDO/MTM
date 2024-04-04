package com.example.mtm.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtm.R;
import com.example.mtm.activity.NotificationContentActivity;
import com.example.mtm.model.NotificationsModel;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context mContext;
    private List<NotificationsModel> mItems;
    private final OnItemClickListener listener;
    public NotificationsAdapter(Context context, List<NotificationsModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_notification_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationsModel itemData = mItems.get(position);

        holder.titleTextView.setText(itemData.getTitle());
        holder.bodyTextView.setText(itemData.getBody());
        holder.dateTextView.setText(itemData.getDate() + "\n" + itemData.getTime());
        holder.view.setVisibility(itemData.isRead() ? View.INVISIBLE : View.VISIBLE);

//        holder.itemView.setOnClickListener(view -> {
//            if (!itemData.isRead()) {
//                MyUtils.openLink(itemData.getUrl(), mContext);
//            }
//
//        });
//        holder.itemView.setOnClickListener(view -> {
//            if (!itemData.getUrl().equals("")) {
//                showSubscriptionDialog(itemData.getUrl(), position);
//            }
//            else {
//                showPopupDialog2(position);
//            }
//
//        });
        holder.itemView.setOnClickListener(view -> {
//            openNotificationActivity(itemData.getTitle(), itemData.getBody(), itemData.getUrl(), itemData.getId());
            listener.onItemClick(itemData.getTitle(), itemData.getBody(), itemData.getUrl(), itemData.getId(), itemData.getDate(), itemData.getTime());
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView bodyTextView;
        TextView dateTextView;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            view = itemView.findViewById(R.id.view);

        }
    }

    public void addItems(List<NotificationsModel> newItems) {
        int startPosition = mItems.size(); // Get the current size of the list
        mItems.addAll(newItems); // Add new items to the existing list
        notifyItemRangeInserted(startPosition, newItems.size()); // Notify adapter about the newly added items
    }


    private void showPopupDialog(String url, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bildirim İşlemi")
                .setMessage("Ne yapmak istersiniz?")
                .setPositiveButton("Linke Git", (dialog, which) -> MyUtils.openLink(url, mContext))
                .setNegativeButton("Okundu", (dialog, which) -> {
                    // Update item as read
                    mItems.get(position).setRead(true);
                    // Notify adapter about the change
                    notifyItemChanged(position);
//                    listener.onItemClick(mItems.get(position).getId());
                })
                .setCancelable(true)
                .show();
    }

    private void showPopupDialog2(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bildirim İşlemi")
                .setMessage("Ne yapmak istersiniz?")
                .setPositiveButton("Okundu", (dialog, which) -> {
                    // Update item as read
                    mItems.get(position).setRead(true);
                    // Notify adapter about the change
                    notifyItemChanged(position);
//                    listener.onItemClick(mItems.get(position).getId());
                })
                .setCancelable(true)
                .show();
    }

    private void openNotificationActivity(String title, String body, String link, int id) {
        Intent intent = new Intent(mContext, NotificationContentActivity.class);

        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("link", link);
        intent.putExtra("id", id);

        Bundle options = ActivityOptions.makeCustomAnimation(mContext, R.anim.left, R.anim.right).toBundle();
        mContext.startActivity(intent, options);
    }

    public interface OnItemClickListener {
        void onItemClick(String title, String body, String link, int id, String date, String time);
    }
}
