package com.brumaireparis.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags;
            if (isBlack) {
                //更改文字颜色为深黑色
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            else {
                //更改文字颜色为浅色
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decor.setSystemUiVisibility(flags);
        }
    }

    public void calculatorClick(View v)
    {
        Intent intent = new Intent(MainActivity.this,Calculator.class);
        startActivity(intent);

        //MainActivity.this.finish();
    }

    public void flashlightClick(View v)
    {
        Intent intent = new Intent(MainActivity.this,Flashlight.class);
        startActivity(intent);

    }

    public void callClick(View v)
    {
        Intent intent = new Intent(MainActivity.this,Phonecall.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeStatusBarTextColor(this.getWindow(),true);

        WebView webView = findViewById(R.id.WeatherWebView);

        webView.setOnTouchListener((v, event) -> (event.getAction() == MotionEvent.ACTION_MOVE));
        //webView.loadUrl(GlobalVar.WEATHERWEBVIEW_IMG);

        webView.loadUrl("https://m.tianqi.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}