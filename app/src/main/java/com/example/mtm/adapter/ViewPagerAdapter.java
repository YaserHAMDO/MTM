package com.example.mtm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.mtm.R;
import com.example.mtm.model.SliderModel;
import com.example.mtm.util.MyUtils;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<SliderModel> sliderModels;

    public ViewPagerAdapter(Context context, List<SliderModel> imageUrls) {
        this.context = context;
        this.sliderModels = imageUrls;
    }

    @Override
    public int getCount() {
        return sliderModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_slide, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageViewMain);

        // Load image using Glide from the URL
        Glide.with(context)
                .load(sliderModels.get(position).getImageUrl())
                .into(imageView);

        container.addView(itemView);

        itemView.setOnClickListener(v -> {
            MyUtils.openLink(sliderModels.get(position).getLink(), context);
        });

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
