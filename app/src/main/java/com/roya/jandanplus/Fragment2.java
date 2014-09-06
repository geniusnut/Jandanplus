package com.roya.jandanplus;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2 extends Fragment{

    private FragmentActivity activity;
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_2, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public  void setUserVisibleHint ( boolean isVisibleToUser )  {
        super . setUserVisibleHint ( isVisibleToUser );
        if  ( isVisibleToUser )  {
            if(activity == null) { activity = getActivity(); }
            if(button1  == null) { button1 = (Button)activity.findViewById(R.id.button1); }
            if(button2  == null) { button2 = (Button)activity.findViewById(R.id.button2); }
            if(button3  == null) { button3 = (Button)activity.findViewById(R.id.button3); }
            button2.setTextColor(Color.parseColor("#fac627"));
            button3.setTextColor(Color.parseColor("#a4a4a4"));
            button1.setTextColor(Color.parseColor("#a4a4a4"));
        } else { }
    }
}
