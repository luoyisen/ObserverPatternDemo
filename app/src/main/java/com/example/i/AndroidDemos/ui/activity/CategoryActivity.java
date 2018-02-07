package com.example.i.AndroidDemos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.i.AndroidDemos.R;
import com.example.i.AndroidDemos.base.BaseActivity;
import com.example.i.AndroidDemos.adapter.CategoryDataAdapter;
import com.example.i.AndroidDemos.constant.bean.CategoryDataModel;
import com.example.i.AndroidDemos.constant.bean.CategoryGridModel;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I on 2017/8/30.
 */

public class CategoryActivity extends BaseActivity {

    private GridView mGridView;
    private String nextUrl;
    private String name;
    private String url;

    private LinearLayout ll_load_more;

    private CategoryDataAdapter mCategoryDataAdapter;
    private List<CategoryGridModel> mList = new ArrayList<>();
    private ArrayList<String> mListBigUrl = new ArrayList<>();


    @Override
    protected void initView() {
        mGridView = (GridView) findViewById(R.id.mGridView);
        ll_load_more = (LinearLayout) findViewById(R.id.ll_load_more);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        url = intent.getStringExtra("url");

        if (!TextUtils.isEmpty(name)) {
            getSupportActionBar().setTitle(name);
        }

        if (!TextUtils.isEmpty(url)) {
            getData(url);
        }

        //监听滑动到最后
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mGridView.getLastVisiblePosition() == mGridView.getCount() - 1) {
                        ll_load_more.setVisibility(View.VISIBLE);
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CategoryActivity.this, GalleryActivity.class);
                intent.putExtra("position", i);
                intent.putStringArrayListExtra("bigUrl", mListBigUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public int setLayoutResourceId() {
        return R.layout.activity_category;
    }


    //加载数据
    private void loadData() {
        RxVolley.get(nextUrl, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                ll_load_more.setVisibility(View.GONE);
                parsingJson(t);
                mCategoryDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
            }
        });
    }

    //解析
    private void getData(String url) {
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
                mCategoryDataAdapter = new CategoryDataAdapter(CategoryActivity.this, mList);
                mGridView.setAdapter(mCategoryDataAdapter);
            }

            @Override
            public void onFailure(VolleyError error) {
//                L.i(error.toString());
            }
        });
    }

    private void parsingJson(String t) {
        Gson gson = new Gson();
        CategoryDataModel model = gson.fromJson(t, CategoryDataModel.class);
        nextUrl = model.getLink().getNext();
        for (int i = 0; i < model.getData().size(); i++) {
            CategoryGridModel models = new CategoryGridModel();
            models.setSmall(model.getData().get(i).getSmall());
            models.setDown_stat(model.getData().get(i).getDown_stat());
            models.setDown(model.getData().get(i).getDown());
            models.setBig(model.getData().get(i).getBig());
            mListBigUrl.add(model.getData().get(i).getBig());
            models.setKey(model.getData().get(i).getKey());
            mList.add(models);
        }
    }
}