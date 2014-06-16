package com.github.beothorn;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import rx.functions.Action1;
import sneerteam.snapi.*;

import static sneerteam.snapi.CloudPath.ME;

public class JogodegoActivity extends Activity
{
    private Contact friend;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final WebView myWebView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");

        final Cloud cloud = Cloud.onAndroidMainThread(this);

        myWebView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void printRemote(String message) {
                cloud.path("games", "go", friend.publicKey()).pub(message);
            }
        },"Jogodego");

        ContactPicker.pickContact(this).subscribe(new Action1<Contact>() {@Override public void call(Contact contact) {
            friend = contact;

            cloud.path(friend.publicKey(), "games", "go", ME).value().cast(String.class).subscribe(new Action1<String>() {
                @Override
                public void call(final String value) {
                    myWebView.loadUrl("javascript:testEcho(" + value + ")");
                }
            });
        }});
    }
}
