package com.github.beothorn;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class JogodegoActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WebView mWebView=(WebView)findViewById(R.id.webView);
        mWebView.loadUrl("file:///android_asset/index.html");
    }
}
