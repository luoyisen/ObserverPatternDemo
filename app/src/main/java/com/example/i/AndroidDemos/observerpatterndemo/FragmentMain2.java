package com.example.i.AndroidDemos.observerpatterndemo;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.i.AndroidDemos.R;

/**
 * Created by I on 2017/8/27.
 */

public class FragmentMain2 extends Fragment implements Observer {
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, null);
        tv = (TextView) v.findViewById(R.id.text_showresult);
        MyObserverable.getObserverable().addObserver(this);//
        return v;
    }

    @Override
    public void update(Observable observable, Object data) {//观察者收到通知以后来做具体的事情，所以fragment实现了Observer接口
        final String s = data.toString();
        Handler handler;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText(s);//执行具体的方法
            }
        }, 1000);

    }
}
