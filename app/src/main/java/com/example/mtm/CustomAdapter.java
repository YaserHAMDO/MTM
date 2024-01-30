package com.example.mtm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        return convertView;
    }

    static class ViewHolder {
        ImageView mediaImageView;
        TextView textView;
        LinearLayout linearLayout;
    }
}
