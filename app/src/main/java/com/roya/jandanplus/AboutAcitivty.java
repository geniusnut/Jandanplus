package com.roya.jandanplus;

import android.app.Activity;
import android.os.Bundle;

public class AboutAcitivty extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getActionBar().hide();

    }
}