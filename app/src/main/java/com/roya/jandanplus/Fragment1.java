package com.roya.jandanplus;


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
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        getListView().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d("T", "Action was DOWN");
                        return false;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d("T", "Action was MOVE");
                        return false;
                    case (MotionEvent.ACTION_UP):
                        Log.d("T", "Action was UP");
                        return false;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d("T", "Action was CANCEL");
                        return false;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d("T", "Movement occurred outside bounds " +
                                "of current screen element");
                        return false;
                    default:
                        return false;
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
