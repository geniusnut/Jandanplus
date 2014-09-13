package com.roya.jandanplus;


import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fragment1 extends ListFragment {

    private FragmentActivity activity;
    private Button button1;
    private Button button2;
    private Button button3;
    List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
    JandanParser jandanParser;
    SimpleAdapter adapter;
    int Jandanpage = 0;
    boolean JandanIsParseing = false;
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

        //添加空白顶部区域
        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.header_view, null);
        listView.addHeaderView(headerView);
        listView.addFooterView(headerView);

        //初始化浮动按钮
        //imageButton.setAlpha((float)0.8);

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
                            if(adapter.getCount() - 6 <= listView.getFirstVisiblePosition()){
                                if (!JandanIsParseing) {
                                    new listviewSeter().execute(++Jandanpage);
                                }
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


        adapter = new SimpleAdapter(getActivity(), items, R.layout.fragment1_item,
                new String[]{"link", "image", "title", "by", "tag", "cont"},
                new int[]{R.id.link, R.id.image, R.id.title, R.id.by, R.id.tag, R.id.cont});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(
                    View view,
                    Object data,
                    String textRepresentation) {
                if ((view instanceof ImageView) && (data instanceof Bitmap)) {
                    ImageView imageView = (ImageView) view;
                    Bitmap bmp = (Bitmap) data;
                    imageView.setImageBitmap(bmp);
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);

        jandanParser = new JandanParser(getActivity().getApplicationContext());
        new listviewSeter().execute(++Jandanpage);
    }

    private class listviewSeter extends AsyncTask<Integer, Void, List<Map<String, Object>>>{
        @Override
        protected List<Map<String, Object>> doInBackground(Integer... page) {

            JandanIsParseing = true;
            return jandanParser.JandanHomePage(page[0]);
        }

        protected void onPostExecute(List<Map<String, Object>> result) {

            items.addAll(result);
            adapter.notifyDataSetChanged();
            JandanIsParseing = false;
        }
    }

    @Override
    public  void setUserVisibleHint ( boolean isVisibleToUser )  {
        super . setUserVisibleHint ( isVisibleToUser );
        if  ( isVisibleToUser )  {
            if(activity == null) { activity = getActivity(); }
            if(button1  == null) { button1 = (Button)activity.findViewById(R.id.button1); }
            if(button2  == null) { button2 = (Button)activity.findViewById(R.id.button2); }
            if(button3  == null) { button3 = (Button)activity.findViewById(R.id.button3); }
            button1.setTextColor(Color.parseColor("#fdbc40"));
            button3.setTextColor(Color.parseColor("#a4a4a4"));
            button2.setTextColor(Color.parseColor("#a4a4a4"));
            getActivity().getActionBar().show();
        } else { }
    }
}
