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
import com.example.mtm.model.MediaReportModel;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class MediaReportAdapter extends RecyclerView.Adapter<MediaReportAdapter.ViewHolder> {

    private Context mContext;
    private List<MediaReportModel> mItems;
    private final OnItemClickListener listener;
    public MediaReportAdapter(Context context, List<MediaReportModel> items, OnItemClickListener listener) {
        mContext = context;
        mItems = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_media_report_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaReportModel itemData = mItems.get(position);


        holder.date.setText(itemData.getDate());
        holder.title.setText(itemData.getTitle());
        holder.program.setText(itemData.getTitle());


        holder.itemView.setOnClickListener(view -> {
            MyUtils.openLink(itemData.getLink(), mContext);
//            listener.onItemClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView title;
        TextView program;

        ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            program = itemView.findViewById(R.id.program);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
