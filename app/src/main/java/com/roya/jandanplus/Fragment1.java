package com.roya.jandanplus;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
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
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
    ImageButton imageButton;
    RotateAnimation rotateAnimation;
    ActionLayout al;

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
        imageButton = (ImageButton) getActivity().findViewById(R.id.imageButton);
        final float d = getActivity().getResources().getDisplayMetrics().density;

        al = (ActionLayout) getActivity().findViewById(R.id.action_layout);
        al.setViewHeight(96);
        al.setAnimationDuration(200);
        al.setHiddenOrientation(al.HIDDEN_TOP);

        //添加空白区域
        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View headerView = lif.inflate(R.layout.header_view, null);
        listView.addHeaderView(lif.inflate(R.layout.header_view, null));
        listView.addFooterView(lif.inflate(R.layout.footer_view, null));

        //处理点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView link = (TextView) view.findViewById(R.id.link);
                String a = link.getText().toString();
                Intent intent = new Intent(getActivity(),PostActivity.class);
                intent.putExtra("link",a);
                startActivity(intent);
            }
        });

        //初始化浮动按钮
        final TranslateAnimation translateAnimationDown = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 92f * d);
        translateAnimationDown.setDuration(250);

        final TranslateAnimation translateAnimationUp = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 92f * d,
                Animation.ABSOLUTE, 0f);
        translateAnimationUp.setDuration(250);

        translateAnimationDown.setFillAfter(true);
        translateAnimationUp.setFillAfter(true);

        rotateAnimation = new RotateAnimation(0, 2160,Animation.RELATIVE_TO_SELF+36*d,Animation.RELATIVE_TO_SELF+36*d);
        rotateAnimation.setDuration(1800);
        imageButton.startAnimation(rotateAnimation);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton.startAnimation(rotateAnimation);
                Jandanpage = 0;
                if (!listView.isStackFromBottom()) {
                    listView.setStackFromBottom(true);
                }
                listView.setStackFromBottom(false);
                new listviewSeter().execute(++Jandanpage);
            }
        });

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
                                al.show();
                            }

                        } else if (listView.getFirstVisiblePosition() != vPstition) {
                            actionbar.hide();
                            if (animationIsNotRuning[0] && (imageButton.getVisibility() == View.VISIBLE)) {
                                imageButton.startAnimation(translateAnimationDown);
                                al.hide();
                            }
                            if(adapter.getCount() - 8 <= listView.getFirstVisiblePosition()){
                                if (!JandanIsParseing) {
                                    new listviewSeter().execute(++Jandanpage);
                                }
                            }
                        }
                    } else {
                        actionbar.show();
                        if (animationIsNotRuning[0] && (imageButton.getVisibility() == View.INVISIBLE)) {
                            imageButton.startAnimation(translateAnimationUp);
                            al.show();
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
        jandanParser.setOnImageChangedlistener(new JandanParser.OnImageChangedlistener() {
            @Override
            public void OnImageChanged() {
                new notifyDataSetChanged().execute();
            }
        });

    }

    private class notifyDataSetChanged extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        protected void onPostExecute(Void voids){
            adapter.notifyDataSetChanged();
        }
    }

    private class listviewSeter extends AsyncTask<Integer, Void, List<Map<String, Object>>>{
        @Override
        protected List<Map<String, Object>> doInBackground(Integer... page) {
            JandanIsParseing = true;
            List<Map<String, Object>> list = jandanParser.JandanHomePage(page[0]);
            if (page[0] == 1){
                items.clear();
            }
            return list;
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
            button1.setTextColor(Color.parseColor("#ffffffff"));
            button3.setTextColor(Color.parseColor("#88ffffff"));
            button2.setTextColor(Color.parseColor("#88ffffff"));
            getActivity().getActionBar().show();
        } else { }
    }
}
