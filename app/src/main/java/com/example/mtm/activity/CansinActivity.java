package com.example.mtm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mtm.R;
import com.example.mtm.util.DataHolder;
import com.example.mtm.util.MyUtils;
import com.example.mtm.util.ZoomClass;
import com.example.mtm.util.ZoomClassWebView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CansinActivity extends AppCompatActivity implements ZoomClassWebView.ZoomClassListener2 {

    private int index, count;
    private ArrayList<String> CansinUrlArray;
    private ArrayList<String> CansinShareUrlArray;
    private ArrayList<String> CansinDatesArray;
    private ArrayList<String> CansinNamesArray;
    ZoomClassWebView webView;
    private ProgressBar progressBar2;

    LinearLayout source, test3, test12;

    TextView sourceTextView;


    ImageView cancel, paylas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cansin);

        progressBar2 = findViewById(R.id.progressBar2);

        source = findViewById(R.id.source);
        test3 = findViewById(R.id.test3);
        test12 = findViewById(R.id.test12);
        sourceTextView = findViewById(R.id.sourceTextView);
        cancel = findViewById(R.id.cancel);
        paylas = findViewById(R.id.paylas);

        webView = findViewById(R.id.webView2);
        webView.setZoomClassListener2(this);


        Intent intent = getIntent();
        index = intent.getIntExtra("index2", 0);

        CansinUrlArray = DataHolder.getInstance().getCansinUrlArray();
        CansinShareUrlArray = DataHolder.getInstance().getCansinShareUrlArray();
        CansinDatesArray = DataHolder.getInstance().getCansinDatesArray();
        CansinNamesArray = DataHolder.getInstance().getCansinNamesArray();

        count = CansinUrlArray.size();


        loadImage();



        paylas.setOnClickListener(view -> {
            MyUtils.shareLink(CansinShareUrlArray.get(index), this);
        });

        source.setOnClickListener(view -> {
            MyUtils.openLink(CansinUrlArray.get(index), this);
        });

        cancel.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        test3.setOnClickListener(view -> {
//            MyUtils.shareLink(CansinUrlArray.get(index), this);
        });

        test12.setOnClickListener(view -> {
//            MyUtils.shareLink(CansinUrlArray.get(index), this);
        });

    }

    private void loadImage() {


        sourceTextView.setText(CansinNamesArray.get(index) + "\n" + MyUtils.changeDateFormat(CansinDatesArray.get(index)));

        progressBar2.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);


        test3.setVisibility(View.INVISIBLE);
        test12.setVisibility(View.INVISIBLE);


        // Load the URL in the WebView
        webView.loadUrl(CansinUrlArray.get(index));

        // Set up a WebViewClient to handle page loading events
        webView.setWebViewClient(new WebViewClient() {
            // Show ProgressBar when page starts loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                progressBar2.setVisibility(View.VISIBLE);
            }

            // Hide ProgressBar when page finishes loading
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar2.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);


            }
        });

    }


    @Override
    public void onSwipeRight2() {
        if (index > 0) {
            index--;
            loadImage();
        }
        else {
//            text.animate().
////                        alpha(0.7f).
//        scaleX(1.2f).
//                    scaleY(1.2f).
//                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
        }


    }



    @Override
    public void onSwipeLeft2() {

        if (index < count - 1) {
            index++;
            loadImage();
        }
        else {
//            text.animate().
////                        alpha(0.7f).
//        scaleX(1.2f).
//                    scaleY(1.2f).
//                    setDuration(100).withEndAction(() -> text.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100));
        }

    }

    @Override
    public void onSwipeDown2() {

    }

    @Override
    public void onSwipeUp2() {

    }

    @Override
    public void onSingleTapUp2() {

        if (test3.getVisibility() == View.VISIBLE) {
            test3.setVisibility(View.INVISIBLE);
            test12.setVisibility(View.INVISIBLE);
        }
        else {
            test3.setVisibility(View.VISIBLE);
            test12.setVisibility(View.VISIBLE);
        }

    }
}