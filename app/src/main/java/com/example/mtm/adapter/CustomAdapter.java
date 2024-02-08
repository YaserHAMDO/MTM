package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mtm.R;
import com.example.mtm.model.ItemData;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<ItemData> mItems;

    public CustomAdapter(Context context, List<ItemData> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, parent, false);
            holder = new ViewHolder();
            holder.mediaImageView = convertView.findViewById(R.id.xxxx);
            holder.textView = convertView.findViewById(R.id.yyyy);
            holder.linearLayout = convertView.findViewById(R.id.linearLayout);
            holder.progressView = convertView.findViewById(R.id.progressBar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemData itemData = mItems.get(position);

        // Set ImageView
        holder.mediaImageView.setImageResource(itemData.getImageResId());

        // Set background color for LinearLayout
        holder.linearLayout.setBackgroundResource(itemData.getBackgroundColor());

        // Set text
        holder.textView.setText(itemData.getText());

        // Set progress bar visibility
        holder.progressView.setVisibility(itemData.isProgressBarVisible() ? View.VISIBLE : View.GONE);
        holder.mediaImageView.setVisibility(itemData.isProgressBarVisible() ? View.GONE : View.VISIBLE);

        return convertView;
    }

    static class ViewHolder {
        ImageView mediaImageView;
        TextView textView;
        LinearLayout linearLayout;
        ProgressBar progressView;
    }
}
