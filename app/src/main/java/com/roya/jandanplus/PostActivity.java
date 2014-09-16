package com.roya.jandanplus;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PostActivity extends Activity {

    String link ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        link = getIntent().getStringExtra("link");
        WebView webview = (WebView)this.findViewById(R.id.webview);
        //webview.loadUrl(link);
        //webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
    }
}
