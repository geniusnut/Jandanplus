package com.roya.jandanplus;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment2 extends ListFragment {

    private FragmentActivity activity;
    private Button button1;
    private Button button2;
    private Button button3;


    JandanParser jandanParser;
    boolean JandanIsParseing = false;

    ActionLayout al;
    SimpleAdapter adapter;
    List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_2, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bl) {
        super.onActivityCreated(bl);
        //添加空白区域
        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getListView().addHeaderView(lif.inflate(R.layout.header_view, null));
        getListView().addFooterView(lif.inflate(R.layout.footer_view, null));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        al = (ActionLayout) getActivity().findViewById(R.id.action_layout);
        al.setViewHeight(96);
        al.setAnimationDuration(200);
        al.setHiddenOrientation(al.HIDDEN_TOP);

        jandanParser = new JandanParser(getActivity().getApplicationContext());
/*
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("updater","roya");
        item.put("time","1-min ago");
        item.put("text","!!!!!");
        item.put("image",R.drawable.loading);
        item.put("xx","4");
        item.put("oo","13");
        item.put("comm","2");

        items.add(item);
        items.add(item);
        items.add(item);
        items.add(item);
*/
        adapter = new SimpleAdapter(getActivity(), items, R.layout.pics_fm2,
                new String[]{"updater", "time", "text", "image", "xx", "oo","comm"},
                new int[]{R.id.updater, R.id.time, R.id.text, R.id.image, R.id.xx, R.id.oo, R.id.comm});

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

        new picSeter().execute(0);
    }

    private class picSeter extends AsyncTask<Integer, Void, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(Integer... page) {
            JandanIsParseing = true;
            List<Map<String, Object>> list = jandanParser.JandanPicPage(page[0]);

            return list;

        }
        protected void onPostExecute(List<Map<String, Object>> result) {
            if(result.isEmpty()){
                Toast.makeText(getActivity(), "无法连接到服务器，请稍后再试", Toast.LENGTH_SHORT).show();
            }
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
            button2.setTextColor(Color.parseColor("#ffffffff"));
            button3.setTextColor(Color.parseColor("#88ffffff"));
            button1.setTextColor(Color.parseColor("#88ffffff"));
            getActivity().getActionBar().show();
            if (al.getVisibility() == View.INVISIBLE){
                al.show();}
        } else { }
    }
}
