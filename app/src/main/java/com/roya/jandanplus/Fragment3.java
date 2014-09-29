package com.roya.jandanplus;


import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class Fragment3 extends ListFragment {

    private FragmentActivity activity;
    private Button button1;
    private Button button2;
    private Button button3;
    ListView listView;
    ActionBar actionbar;
    ImageButton imageButton;

    Animation animation;

    boolean isVisibleToUser = false;

    JandanParser jandanParser;
    int JandanPicPage = 0;
    boolean JandanIsParseing = false;

    ActionLayout al;
    ActionLayout aimageButton;
    SimpleAdapter adapter;
    List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_3, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityCreated(Bundle bl) {
        super.onActivityCreated(bl);

        final float d = getActivity().getResources().getDisplayMetrics().density;

        //添加空白区域
        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getListView().addHeaderView(lif.inflate(R.layout.header_view, null));
        getListView().addFooterView(lif.inflate(R.layout.footer_view, null));


        //imageButton
        aimageButton = (ActionLayout) getActivity().findViewById(R.id.aimageButton_fm3);
        aimageButton.setViewHeight(92);
        aimageButton.setAnimationDuration(250);
        aimageButton.setHiddenOrientation(al.HIDDEN_BOTTOM);
        imageButton = (ImageButton) getActivity().findViewById(R.id.refresh_fm3_btn);
        animation =  AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButton.getVisibility() == View.VISIBLE){
                    imageButton.startAnimation(animation);
                }
                JandanPicPage = 0;
                items.clear();
                adapter.notifyDataSetChanged();
                new picSeter().execute(JandanPicPage);
                JandanPicPage++;
            }
        });

        //处理滚动
        listView = getListView();
        actionbar = getActivity().getActionBar();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int vPstition = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if ((actionbar != null) && isVisibleToUser) {
                    if (listView.getFirstVisiblePosition() > 0) {
                        if (listView.getFirstVisiblePosition() < vPstition) {
                            actionbar.show();
                            al.show();
                            aimageButton.show();

                        } else if (listView.getFirstVisiblePosition() != vPstition) {
                            actionbar.hide();
                            al.hide();
                            aimageButton.hide();

                            if(adapter.getCount() - 8 <= listView.getFirstVisiblePosition()){
                                if (!JandanIsParseing) {
                                    new picSeter().execute(JandanPicPage);
                                    JandanPicPage++;
                                }
                            }
                        }
                    } else {
                        actionbar.show();
                        al.show();
                        aimageButton.show();
                    }
                    vPstition = listView.getFirstVisiblePosition();
                }
            }
        });
    }


        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        al = (ActionLayout) getActivity().findViewById(R.id.action_layout);
        al.setViewHeight(96);
        al.setAnimationDuration(200);
        al.setHiddenOrientation(al.HIDDEN_TOP);
        jandanParser = new JandanParser(getActivity().getApplicationContext());

        adapter = new SimpleAdapter(getActivity(), items, R.layout.fm2_list_item,
                new String[]{"updater", "time", "text", "image", "xx", "oo"},
                new int[]{R.id.updater, R.id.time, R.id.text, R.id.image, R.id.xx, R.id.oo});

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

        jandanParser.setOnImageChangedlistener(new JandanParser.OnImageChangedlistener() {
            @Override
            public void OnImageChanged() {
                new notifyDataSetChanged().execute();
            }
        });
    }

    private class notifyDataSetChanged extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        protected void onPostExecute(Void voids){
            adapter.notifyDataSetChanged();
        }
    }

    private class picSeter extends AsyncTask<Integer, Void, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(Integer... page) {
            JandanIsParseing = true;
            List<Map<String, Object>> list = jandanParser.JandanOoxxPage(page[0]);
            if (page[0] == 0){
                items.clear();
            }
            return list;

        }
        protected void onPostExecute(List<Map<String, Object>> result) {
            if(result.isEmpty()){
                Toast.makeText(getActivity(), "载入出错了！请稍后再试。", Toast.LENGTH_SHORT).show();
            }
            items.addAll(result);
            adapter.notifyDataSetChanged();
            JandanIsParseing = false;
            if(adapter.getCount() <= 10){
                new picSeter().execute(JandanPicPage);
                JandanPicPage++;
            }
        }
    }
    @Override
    public  void setUserVisibleHint ( boolean isVisibleToUser ) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.isVisibleToUser = true;

            if (JandanPicPage == 0){
                new picSeter().execute(JandanPicPage);
                JandanPicPage++;
                if(imageButton != null) {
                    if (imageButton.getVisibility() == View.VISIBLE) {
                        imageButton.startAnimation(animation);
                    }
                }
            }
            if (activity == null) {
                activity = getActivity();
            }
            if (button1 == null) {
                button1 = (Button) activity.findViewById(R.id.button1);
            }
            if (button2 == null) {
                button2 = (Button) activity.findViewById(R.id.button2);
            }
            if (button3 == null) {
                button3 = (Button) activity.findViewById(R.id.button3);
            }
            button3.setTextColor(Color.parseColor("#ffffffff"));
            button2.setTextColor(Color.parseColor("#88ffffff"));
            button1.setTextColor(Color.parseColor("#88ffffff"));
            getActivity().getActionBar().show();
            if (al.getVisibility() == View.INVISIBLE) {
                al.show();
            }
        } else {
            this.isVisibleToUser = false;
        }
    }


}
