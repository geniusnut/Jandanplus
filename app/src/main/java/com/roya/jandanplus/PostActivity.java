package com.roya.jandanplus;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PostActivity extends Activity {

    Activity postActivity = this;
    String link ;
    String title ;
    String comm;
    WebView webview;
    WebView commwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_post);
        setActionBar();
        link = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra(Intent.EXTRA_TITLE);
        comm = getIntent().getStringExtra("comm");

        TextView mComm = (TextView) findViewById(R.id.btn_post_comm_text);
        mComm.setText(comm);
        webview = (WebView) findViewById(R.id.webview);
        webview.clearCache(true);
        new webViewLoad().execute(link);


        commwebview = (WebView) findViewById(R.id.post_comm);
        commwebview.clearCache(true);
        new commViewLoad().execute(link);


        //评论
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final ActionLayout actionLayout = (ActionLayout) findViewById(R.id.post_actionlayout);
        actionLayout.setAnimationDuration(500);
        actionLayout.setHiddenOrientation(actionLayout.HIDDEN_BOTTOM);
        actionLayout.measure(0,0);
        actionLayout.setViewHeight((int)(metric.heightPixels/metric.density));
        actionLayout.setVisibility(View.INVISIBLE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.btn_post_comm);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionLayout.getVisibility() == View.VISIBLE){
                actionLayout.hide();
                }else {
                    actionLayout.show();
                }
            }
        });

        //分享
        ImageButton share_btn = (ImageButton) findViewById(R.id.btn_post_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, title + "\n" + link);
                Intent chooserIntent = Intent.createChooser(intent, "Share");
                startActivity(chooserIntent);
            }
        });
    }

    private class webViewLoad extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            PostFormater postFormater = new PostFormater(getApplicationContext());
            return postFormater.postFormater(strings[0]);
        }
        protected void onPostExecute(String data){
            webview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        }
    }

    private class commViewLoad extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            PostFormater postFormater = new PostFormater(getApplicationContext());
            return postFormater.commFormater(strings[0]);
        }
        protected void onPostExecute(String data){
            commwebview.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        }
    }

    private void setActionBar() {
        LayoutInflater inflater = (LayoutInflater) getActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View customActionBarView = inflater.inflate(R.layout.actionbar_post, null);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout btn_back = (LinearLayout)findViewById(R.id.btn_post_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postActivity.finish();
            }
        });
    }
}