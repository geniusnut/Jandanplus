package com.roya.jandanplus;


import android.app.ActionBar;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment1 extends ListFragment {

    private FragmentActivity activity;
    private Button button1;
    private Button button2;
    private Button button3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_1, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bl){
        super.onActivityCreated(bl);

        final ListView listView = getListView();
        final ActionBar actionbar = getActivity().getActionBar();
        final ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.imageButton);
        final float d = getActivity().getResources().getDisplayMetrics().density;

        imageButton.setAlpha((float)0.8);



        final TranslateAnimation translateAnimationDown = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 88f * d);
        translateAnimationDown.setDuration(300);

        final TranslateAnimation translateAnimationUp = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 88f * d,
                Animation.ABSOLUTE, 0f);
        translateAnimationUp.setDuration(300);

        translateAnimationDown.setFillAfter(true);
        translateAnimationUp.setFillAfter(true);

        final boolean[] animationIsNotRuning = {true};
        translateAnimationDown.setAnimationListener(new Animation.AnimationListener() {
             @Override
             public void onAnimationStart(Animation animation) {
                animationIsNotRuning[0] = false;
             }

             @Override
             public void onAnimationEnd(Animation animation) {
                 imageButton.setVisibility(View.INVISIBLE);
                 imageButton.clearAnimation();
                 animationIsNotRuning[0] = true;

             }

             @Override
             public void onAnimationRepeat(Animation animation) {

             }
        });
        translateAnimationUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationIsNotRuning[0] = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageButton.setVisibility(View.VISIBLE);
                imageButton.clearAnimation();
                animationIsNotRuning[0] = true;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int vPstition = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if (actionbar != null) {
                    if (listView.getFirstVisiblePosition() > 0) {
                        if (listView.getFirstVisiblePosition() < vPstition) {
                            actionbar.show();
                            if (animationIsNotRuning[0] && (imageButton.getVisibility() == View.INVISIBLE)) {
                                imageButton.startAnimation(translateAnimationUp);
                            }

                        } else if (listView.getFirstVisiblePosition() != vPstition) {
                            actionbar.hide();
                            if (animationIsNotRuning[0] && (imageButton.getVisibility() == View.VISIBLE)) {
                                imageButton.startAnimation(translateAnimationDown);
                            }
                        }
                    } else {
                        actionbar.show();
                        if (animationIsNotRuning[0] && (imageButton.getVisibility() == View.INVISIBLE)) {
                            imageButton.startAnimation(translateAnimationUp);
                        }
                    }
                    vPstition = listView.getFirstVisiblePosition();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();

        Map<String, Object> item = new HashMap<String, Object>();
        for (int i = 0; i < 20; i++){
            item.put("image", R.drawable.loading);
            item.put("title", "Title");
            item.put("by", "roya");
            item.put("cont","✉"+"17");
            items.add(item);
        }

        setListAdapter(new SimpleAdapter(getActivity(),items,R.layout.fragment1_item,
                new String[] { "image", "title", "by", "cont"},
                new int[] { R.id.image,R.id.title, R.id.by, R.id.cont} ));
    }

    @Override
    public  void setUserVisibleHint ( boolean isVisibleToUser )  {
        super . setUserVisibleHint ( isVisibleToUser );
        if  ( isVisibleToUser )  {
            if(activity == null) { activity = getActivity(); }
            if(button1  == null) { button1 = (Button)activity.findViewById(R.id.button1); }
            if(button2  == null) { button2 = (Button)activity.findViewById(R.id.button2); }
            if(button3  == null) { button3 = (Button)activity.findViewById(R.id.button3); }
            button1.setTextColor(Color.parseColor("#fabf3d"));
            button3.setTextColor(Color.parseColor("#a4a4a4"));
            button2.setTextColor(Color.parseColor("#a4a4a4"));
        } else { }
    }
}
