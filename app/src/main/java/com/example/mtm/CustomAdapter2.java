package com.example.mtm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {

    private Context context;
    private List<String> dataList;
    private int selectedItem = 0;

    public CustomAdapter2(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        String item = dataList.get(pos);
        holder.textView.setText(item);

        // Change text color if the item is selected
        if (selectedItem == pos) {
            holder.textView.setTextColor(context.getColor(R.color.black));
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.textView.setTextColor(context.getColor(R.color.color1));
            holder.view.setVisibility(View.INVISIBLE);
        }

        // Set item click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the view
                if (selectedItem == pos) {
                    selectedItem = -1; // Deselect item
                } else {
                    selectedItem = pos; // Select item
                }

                // Notify adapter that data has changed
                notifyDataSetChanged();

                // Show a toast with the clicked item
                Toast.makeText(context, "Clicked: " + dataList.get(pos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.greeting_tv1);
            view = itemView.findViewById(R.id.viewxx);
        }
    }
}
