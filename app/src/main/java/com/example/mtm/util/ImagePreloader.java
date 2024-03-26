package com.example.mtm.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mtm.activity.SelimActivity;
import com.example.mtm.adapter.ColumnistAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImagePreloader {

    private Context context;
    private ImageView imageViews;
    private ArrayList<String> imageUrls;
    private int currentIndex;
    private Map<Integer, Boolean> preloadedIndexes;
    private RequestOptions options;
    private int i, j;
    private final progressBarVisibility listener;

    public ImagePreloader(Context context, ImageView imageViews, ArrayList<String> imageUrls, progressBarVisibility listener) {
        this.context = context;
        this.imageViews = imageViews;
        this.imageUrls = imageUrls;
        this.preloadedIndexes = new HashMap<>();
        this.currentIndex = 0;
        this.i = 0;
        this.j = 0;
        this.listener = listener;

        // Setting up Glide options
        options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Cache images in both memory and disk
    }

    public void preloadImages(int selectedNumber) {
        int preloadIndex1 = ((selectedNumber + 1) % imageUrls.size()) + i;
        int preloadIndex2 = ((selectedNumber + 2) % imageUrls.size()) + i;
        int preloadIndex3 = ((selectedNumber - 1 + imageUrls.size()) % imageUrls.size()) - j;
        int preloadIndex4 = ((selectedNumber - 2 + imageUrls.size()) % imageUrls.size()) - j;


        System.out.println("Selim bebek selectedNumber: " + selectedNumber);


        if (!preloadedIndexes.containsKey(preloadIndex1) && preloadIndex1 < imageUrls.size()) {
            Glide.with(context).load(imageUrls.get(preloadIndex1)).apply(options).preload();
            preloadedIndexes.put(preloadIndex1, true);

            System.out.println("Selim bebek preloadIndex1: " + preloadIndex1);
        }
        if (!preloadedIndexes.containsKey(preloadIndex2) && preloadIndex2 < imageUrls.size()) {
            Glide.with(context).load(imageUrls.get(preloadIndex2)).apply(options).preload();
            preloadedIndexes.put(preloadIndex2, true);
            System.out.println("Selim bebek preloadIndex2: " + preloadIndex2);
        }
        if (!preloadedIndexes.containsKey(preloadIndex3) && preloadIndex3 >= 0) {
            Glide.with(context).load(imageUrls.get(preloadIndex3)).apply(options).preload();
            preloadedIndexes.put(preloadIndex3, true);
            System.out.println("Selim bebek preloadIndex3: " + preloadIndex3);
        }
        if (!preloadedIndexes.containsKey(preloadIndex4) && preloadIndex4 >= 0) {
            Glide.with(context).load(imageUrls.get(preloadIndex4)).apply(options).preload();
            preloadedIndexes.put(preloadIndex4, true);
            System.out.println("Selim bebek preloadIndex4: " + preloadIndex4);
        }

//
//        preloadedIndexes.put(preloadIndex1, true);
//        preloadedIndexes.put(preloadIndex2, true);
//        preloadedIndexes.put(preloadIndex3, true);
//        preloadedIndexes.put(preloadIndex4, true);
//
//        System.out.println("selim bebek selectedNumber: " + selectedNumber );
//
//        if (preloadIndex1 < imageUrls.size()) {
//            Glide.with(context).load(imageUrls.get(preloadIndex1)).apply(options).preload();
//            System.out.println("selim bebek preloadIndex1: " + preloadIndex1);
//        }
//
//        if (preloadIndex2 < imageUrls.size()) {
//            Glide.with(context).load(imageUrls.get(preloadIndex2)).apply(options).preload();
//            System.out.println("selim bebek preloadIndex1: " + preloadIndex1);
//        }
//
//
//        if (j > 0) {
//            Glide.with(context).load(imageUrls.get(preloadIndex3)).apply(options).preload();
//            Glide.with(context).load(imageUrls.get(preloadIndex4)).apply(options).preload();
//
//            System.out.println("selim bebek after: " + preloadIndex3 + ", " + preloadIndex4);
//        }

     }

    public void loadCurrentImage(int currentIndex) {
        this.currentIndex = currentIndex;

        loadImage(imageViews, currentIndex);
    }

    private void loadImage(ImageView imageView, int index) {
//        Glide.with(context)
//                .load(imageUrls.get(index))
//                .apply(options)
//                .into(imageView);


        listener.showProgressBar(true);

        Glide.with(context).load(imageUrls.get(index))
//                .thumbnail()
                .apply(options)
//                    .placeholder(R.drawable.placeholder_image) // Placeholder resource while the image is loading
//                    .error(R.drawable.error_image) // Error resource if the image fails to load
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.showProgressBar(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.showProgressBar(false);
                        preloadImages(currentIndex);

                        return false;
                    }
                })
                .into(imageView);

    }

    public void loadNextImage() {
        i++;

        j++;
        j++;
        j++;


        currentIndex = (currentIndex + 1) % imageUrls.size();
        loadImage(imageViews, currentIndex);
//        preloadImages(currentIndex);
    }

    public void loadPreviousImage() {
        j++;

        i++;
        i++;
        i++;
        currentIndex = (currentIndex - 1 + imageUrls.size()) % imageUrls.size();
        loadImage(imageViews, currentIndex);
//        preloadImages(currentIndex);
    }

    public interface progressBarVisibility {
        void showProgressBar(boolean show);
    }
}
