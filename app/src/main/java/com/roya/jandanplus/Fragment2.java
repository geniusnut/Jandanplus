package com.roya.jandanplus;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page_2, container, false);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onStart();


    }

    @Override
    public  void setUserVisibleHint ( boolean isVisibleToUser )  {
        super . setUserVisibleHint ( isVisibleToUser );
        if  ( isVisibleToUser )  {
            Button button1 = (Button)getActivity().findViewById(R.id.button1);
            Button button2 = (Button)getActivity().findViewById(R.id.button2);
            Button button3 = (Button)getActivity().findViewById(R.id.button3);
            button2.setTextColor(Color.parseColor("#fac627"));
            button1.setTextColor(Color.parseColor("#a4a4a4"));
            button3.setTextColor(Color.parseColor("#a4a4a4"));
        } else { }
    }
}
