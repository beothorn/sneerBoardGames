package com.github.beothorn;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.gson.Gson;
import rx.functions.Action1;
import sneerteam.snapi.Cloud;
import sneerteam.snapi.Contact;
import sneerteam.snapi.ContactPicker;

import static sneerteam.snapi.CloudPath.ME;

public class BoardGameActivity extends Activity
{
    private Contact friend;
    Gson gson = new Gson();

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
            public void play(String playJson) {
                Play play = gson.fromJson(playJson, Play.class);
                cloud.path("games", "board", friend.publicKey()).pub(play);
            }
        },"Remote");

        ContactPicker.pickContact(this).subscribe(new Action1<Contact>() {@Override public void call(Contact contact) {
            friend = contact;

            cloud.path(friend.publicKey(), "games", "board", ME).value().cast(Play.class).subscribe(new Action1<Play>(){@Override public void call(final Play play) {
                    myWebView.loadUrl("javascript:play(" + gson.toJson(play) + ")");
            }});
        }});
    }
}
