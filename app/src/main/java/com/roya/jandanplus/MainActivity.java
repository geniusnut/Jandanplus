package com.roya.jandanplus;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.support.v4.view.ViewPager;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private Button button1;
    private Button button2;
    private Button button3;

    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private Fragment1 mfragment1;
    private Fragment2 mfragment2;
    private Fragment3 mfragment3;

    private static final int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        setActionBar();

        mPager = (ViewPager) findViewById(R.id.pager);

        fragmentList = new ArrayList<Fragment>();
        mfragment1 = new Fragment1();
        mfragment2 = new Fragment2();
        mfragment3 = new Fragment3();
        fragmentList.add(mfragment1);
        fragmentList.add(mfragment2);
        fragmentList.add(mfragment3);
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));

        View.OnClickListener headler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == button1){
                    mPager.setCurrentItem(0);
                }
                else if (view == button2){
                    mPager.setCurrentItem(1);
                }
                else if (view == button3){
                    mPager.setCurrentItem(2);
                }
            }
        };
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button1.setOnClickListener(headler);
        button2.setOnClickListener(headler);
        button3.setOnClickListener(headler);
    }

    private class FragmentPagerAdapter extends FragmentStatePagerAdapter {
        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


    private void setActionBar() {
        LayoutInflater inflater = (LayoutInflater) getActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View customActionBarView = inflater.inflate(R.layout.actionbar, null);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
