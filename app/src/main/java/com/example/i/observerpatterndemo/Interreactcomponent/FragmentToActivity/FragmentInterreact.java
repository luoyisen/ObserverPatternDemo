package com.example.i.observerpatterndemo.Interreactcomponent.FragmentToActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.i.observerpatterndemo.adapter.BaseRVAdapter;
import com.example.i.observerpatterndemo.base.BaseFragmentWithRV;
import com.example.i.observerpatterndemo.network.Rxdemo.FragmentDoubanTop250;

import java.util.ArrayList;

/**
 * Created by I on 2017/8/26.
 */

public class FragmentInterreact extends BaseFragmentWithRV {
    ArrayList arrayList;
    public BaseRVAdapter adapter;
    FragmentLogin loginFragment;
    FragmentDoubanTop250 fragmentDoubanTop250;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginFragment = new FragmentLogin();
        fragmentDoubanTop250 = new FragmentDoubanTop250();
        arrayList = new ArrayList();
        arrayList.add("接口实现Fragment和包含该Fragment的Activity通信 \n" +
                "(仅限于该Fragment和包含它的Activity之间)");
        arrayList.add("Activity之间通过startActivityForResult()方法通信");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        arrayList.add("Csfasfsfasdf");
        adapter = new BaseRVAdapter(arrayList, getActivity().getClass().getSimpleName());
        rv_base_fragment.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        displayFragment(loginFragment, this.toString());
                        if (myListener != null) {
                            myListener.sendContent(this.toString());
                        }
                        break;
                    case 1:
                        displayFragment(fragmentDoubanTop250, "fragment1");
                        if (myListener != null) {
                            myListener.sendContent(this.toString());
                        }
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int pisition) {
            }
        });

    }

    public void displayFragment(Fragment fragment, String tag) {
        if (fragment.isAdded() == false) {
            addFragment(fragment, tag);
        } else {
            showFragment(fragment);
        }
    }


}
