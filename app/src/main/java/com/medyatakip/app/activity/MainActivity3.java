package com.medyatakip.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.medyatakip.app.R;
import com.medyatakip.app.util.DataHolder;
import com.medyatakip.app.util.MyUtils;
import com.medyatakip.app.util.ZoomClassWebView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity3 extends AppCompatActivity implements ZoomClassWebView.ZoomClassListener2 {

    private int index, count;
    private ArrayList<String> CaUrlArray;
    private ArrayList<String> CaShareUrlArray;
    private ArrayList<String> CaDatesArray;
    private ArrayList<String> CaNamesArray;
    ZoomClassWebView webView;
    private ProgressBar progressBar2;

    LinearLayout source, test3, test12;

    TextView sourceTextView;


    ImageView cancel, paylas;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        progressBar2 = findViewById(R.id.progressBar2);

        source = findViewById(R.id.source);
        test3 = findViewById(R.id.test3);
        test12 = findViewById(R.id.test12);
        sourceTextView = findViewById(R.id.sourceTextView);
        cancel = findViewById(R.id.cancel);
        paylas = findViewById(R.id.paylas);

        webView = findViewById(R.id.webView2);
        webView.setZoomClassListener2(this);
        webView.setWebViewClient(new CustomWebViewClient2());

        Intent intent = getIntent();
        index = intent.getIntExtra("index2", 0);

        CaUrlArray = DataHolder.getInstance().getCaUrlArray();
        CaShareUrlArray = DataHolder.getInstance().getCaShareUrlArray();
        CaDatesArray = DataHolder.getInstance().getCaDatesArray();
        CaNamesArray = DataHolder.getInstance().getCaNamesArray();

        count = CaUrlArray.size();


        // Enable JavaScript and hardware acceleration
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);


        loadUrl(CaUrlArray.get(index));

//        loadImage();
//        preloadImages();


        paylas.setOnClickListener(view -> {
            MyUtils.shareLink(CaShareUrlArray.get(index), this);
        });

        source.setOnClickListener(view -> {
            MyUtils.openLink(CaUrlArray.get(index), this);
        });

        cancel.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        test3.setOnClickListener(view -> {
//            MyUtils.shareLink(CaUrlArray.get(index), this);
        });

        test12.setOnClickListener(view -> {
//            MyUtils.shareLink(CaUrlArray.get(index), this);
        });

    }

    private void loadImage() {


        sourceTextView.setText(CaNamesArray.get(index) + "\n" + CaDatesArray.get(index));

        progressBar2.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);


        test3.setVisibility(View.INVISIBLE);
        test12.setVisibility(View.INVISIBLE);




        // Load the URL in the WebView
        webView.loadUrl(CaUrlArray.get(index));

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
                test12.setVisibility(View.VISIBLE);
                test3.setVisibility(View.VISIBLE);

                if (index > CaUrlArray.size()) {
                    WebView view2 = new WebView(MainActivity3.this);
                    view2.loadUrl(CaUrlArray.get(index + 1));
                }
//                if (index > 1) {
//                    WebView view2 = new WebView(Activity3.this);
//                    view.loadUrl(CaUrlArray.get(index - 1));
//                }



//                preloadImages();

            }
        });

    }


    private void preloadImages() {
        // Preload all URLs in CaUrlArray
        for (String url : CaUrlArray) {
            WebView preloadWebView = new WebView(this);
            preloadWebView.loadUrl(url);
            preloadWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // Check if the parent view of the WebView is not null before removing it
                    ViewGroup parentView = (ViewGroup) preloadWebView.getParent();
                    if (parentView != null) {
                        parentView.removeView(preloadWebView);
                    }
                }
            });
        }
    }


    @Override
    public void onSwipeRight2() {
        if (index > 0) {
            index--;
            loadUrl(CaUrlArray.get(index));

//            loadImage();
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
            loadUrl(CaUrlArray.get(index));
//            loadImage();
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

    private class CustomWebViewClient extends WebViewClient {
        private final Map<String, byte[]> cache = new HashMap<>();

        @Override
        public void onPageFinished(WebView view, String url) {
            // Hide loading indicator
            progressBar2.setVisibility(View.GONE);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (cache.containsKey(url)) {
                // If resource is cached, return it
                byte[] data = cache.get(url);
                return new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(data));
            } else {
                // Otherwise, load the resource and cache it
                try {
                    URLConnection connection = new URL(url).openConnection();
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] data = outputStream.toByteArray();
                    cache.put(url, data);
                    return new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(data));
                } catch (IOException e) {
                    e.printStackTrace();
                    return super.shouldInterceptRequest(view, request);
                }
            }
        }
    }


    private void loadUrl(String url) {
        sourceTextView.setText(CaNamesArray.get(index) + "\n" + CaDatesArray.get(index));
        progressBar2.setVisibility(View.VISIBLE); // Show loading indicator
        webView.loadUrl(url);
    }

    // Example method to navigate to the next URL in the array
    private void navigateToNextUrl() {
        index = (index + 1) % CaUrlArray.size();
        loadUrl(CaUrlArray.get(index));
    }


    private class CustomWebViewClient2 extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            // Hide loading indicator
            progressBar2.setVisibility(View.GONE);

            // Delay navigation until the page has fully loaded
            if (!CaUrlArray.contains(url)) {
                CaUrlArray.add(url);
                // Preload linked pages in the background
                preloadLinkedPages(view);
            }
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // Delay navigation until the page has fully loaded
//            if (CaUrlArray.contains(url)) {
//                // Page has been preloaded, navigate now
//                return false;
//            } else {
//                // Page hasn't been preloaded yet, wait for onPageFinished() callback
//                return true;
//            }
//        }

        private void preloadLinkedPages(WebView view) {
            // Extract links from the current page and preload them in the background
            String javascript = "javascript:(function() {" +
                    "var links = document.getElementsByTagName('a');" +
                    "for (var i = 0; i < links.length; i++) {" +
                    "   var href = links[i].getAttribute('href');" +
                    "   if (href.startsWith('http')) {" +
                    "       var xhr = new XMLHttpRequest();" +
                    "       xhr.open('GET', href, true);" +
                    "       xhr.send();" +
                    "   }" +
                    "}" +
                    "})()";
            view.loadUrl(javascript);
        }
    }

}