package com.example.mtm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    ImageView profileImageView, notificationImageView;
    GridView gridView;
    ViewPagerAdapter mViewPagerAdapter;

    ViewPager mViewPager;

    List<ItemData> items;

    int[] images = {
            R.drawable.test6,
            R.drawable.test6,
            R.drawable.test6
    };

    private Handler handler;
    int page = 0;
    private int delay = 2000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setItemClickListeners();



        items = new ArrayList<>();
        items.add(new ItemData(R.drawable.media_icon, R.drawable.test7, "Medya Raporu"));
        items.add(new ItemData(R.drawable.news_icon, R.drawable.test8, "Haber Listesi"));
        items.add(new ItemData(R.drawable.media2_icon, R.drawable.test9, "Medya Gündemi"));

        items.add(new ItemData(R.drawable.media_icon, R.drawable.test10, "Yazılı"));
        items.add(new ItemData(R.drawable.internet_icon, R.drawable.test11, "İnternet"));
        items.add(new ItemData(R.drawable.visual_and_auditory_icon, R.drawable.test12, "Görsel & İşitsel"));

        items.add(new ItemData(R.drawable.newspapers_icon, R.drawable.test13, "Gazeteler"));
        items.add(new ItemData(R.drawable.magazines_icon, R.drawable.test14, "Dergiler"));
        items.add(new ItemData(R.drawable.opinion_writers_icon, R.drawable.test15, "Köşe Yazarları"));


        CustomAdapter adapter = new CustomAdapter(this, items);
        gridView.setAdapter(adapter);





        CircleIndicator indicator = findViewById(R.id.indicator);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(getApplication(), images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
//        handler = new Handler(Looper.getMainLooper());
        indicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page = position;
            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(LoginActivity.this, ""  + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void init() {
        mViewPager = findViewById(R.id.viewPagerMaina);
        profileImageView = findViewById(R.id.profileImageView);
        notificationImageView = findViewById(R.id.notificationImageView);
        gridView = findViewById(R.id.gridView);

        handler = new Handler(Looper.getMainLooper());
        items = new ArrayList<>();
    }

    private void setItemClickListeners() {

        profileImageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("itemData", "test");
            startActivity(intent);
        });

        notificationImageView.setOnClickListener(view -> {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            ItemData itemData = items.get(position);
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("itemData", itemData.getText());
            startActivity(intent);
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        public void run() {

            if (mViewPagerAdapter.getCount() == page) {
                page = 0;
            }
            else {
                page++;
            }

            mViewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };


}