package com.example.i.AndroidDemos.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i.AndroidDemos.Interreactcomponent.ActivityComponentIntereact;
import com.example.i.AndroidDemos.JAVA_BASE.ActivityJavaBase;
import com.example.i.AndroidDemos.R;
import com.example.i.AndroidDemos.adapter.MainPagerAdapter;
import com.example.i.AndroidDemos.algorithm.TreeViewMethod2.ui.ActivityAlgorithm2;
import com.example.i.AndroidDemos.android_architecture.ActivityArchitecture;
import com.example.i.AndroidDemos.callbackdemo.CallBackActivity;
import com.example.i.AndroidDemos.customizedview.ActivityCustomizedView;
import com.example.i.AndroidDemos.customizedview.customizedview.CanBeBannedViewPager;
import com.example.i.AndroidDemos.datastructure.ActivityDataStructure;
import com.example.i.AndroidDemos.datastructure.fragments.FragmentHashSet;
import com.example.i.AndroidDemos.main.base.BaseView;
import com.example.i.AndroidDemos.main.login.ui.LoginFragment;
import com.example.i.AndroidDemos.network.ActivityNet;
import com.example.i.AndroidDemos.noteandtools.ActivityNoteAndTools;
import com.example.i.AndroidDemos.observerpatterndemo.ActivityObserverPattern;
import com.example.i.AndroidDemos.opengl.ActivityOpenGl;
import com.example.i.AndroidDemos.util.DisplayMetricsConvert;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/***
 * Created by I on 2017/9/24.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, LoginFragment.LoginCallback, BaseView {
    @BindView(R.id.login)
    TextView text_login;
    @BindView(R.id.pager_main)
    CanBeBannedViewPager pager_main;
    @BindViews({R.id.main_trending, R.id.main_searchresult, R.id.main_userinfo, R.id.main_settings})
    List<RadioButton> radioButtonlist;
    //    @BindView(R.id.text_nodata)
//    TextView text_nodata;
    private LoginFragment loginFragment;
    public static DrawerLayout drawerLayout;
    private SearchView searchview_main;
    String[] titles;
    private long firstclicktime = 0;
    float x1 = 0;
    float x2 = 0;

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        text_nodata.setText(stringFromJNI());
        titles = getResources().getStringArray(R.array.titles);
        Fragment[] fragments = new Fragment[4];
        fragments[0] = new FragmentHashSet();
        fragments[1] = new FragmentHashSet();
        fragments[2] = new FragmentHashSet();
        fragments[3] = new FragmentHashSet();

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), titles, fragments);
        pager_main.setAdapter(adapter);
        pager_main.setOffscreenPageLimit(adapter.getCount() - 1);// TODO: 2017/9/30 to un
        pager_main.setPageMargin(getResources().getDimensionPixelSize(R.dimen.dimen_5dp));
//        pager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                radioButtonlist.get(position).setChecked(true);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
        radioButtonlist.get(0).setChecked(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutmain);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolabr_main);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setElevation(10.0f);
        }
        //这里的SearchView通过自定义style来控制搜索框中的搜索图标不显示
        searchview_main = findViewById(R.id.serchview_main);
        //SearchView的三种显示模式
        searchview_main.setIconifiedByDefault(false);
//        searchview_main.setIconified(false);
//        searchview_main.onActionViewExpanded();//后两种会自动打开软键盘，如果需要设置SearchView默认为不打开状态，则不能使用该模式，否则就算用该模式并设置取消SearchView焦点的方法，会导致软键盘弹出来并退出来一次。
        searchview_main.setQueryHint("search repos from github");
        //设置搜索框背景和大小
        LinearLayout ll_searchview = findViewById(R.id.search_edit_frame);
        ll_searchview.setBackground(getResources().getDrawable(R.drawable.shape_searchview));
        ll_searchview.setLayoutParams(new LinearLayout.LayoutParams(DisplayMetricsConvert.pxToDp(getApplicationContext(), 260), DisplayMetricsConvert.pxToDp(getApplicationContext(), 32)));
        //用来设置输入完成以后是否显示跳转按钮
        searchview_main.setSubmitButtonEnabled(false);
        //ImageView closeViewIcon = (ImageView)searchview_main.findViewById(R.id.search_close_btn);
        //closeViewIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_searching_black_24dp));
        //用来设置searchView展开和不展开状态下的图标和
        LinearLayout search_bar = findViewById(R.id.search_bar);
        search_bar.setGravity(Gravity.CENTER);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.note:
                        startActivity(new Intent(MainActivity.this, ActivityNoteAndTools.class));
                        break;
                    case R.id.huidiao:
                        startActivity(new Intent(MainActivity.this, CallBackActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    case R.id.componentinterreact:
                        startActivity(new Intent(MainActivity.this, ActivityComponentIntereact.class));
                        break;
                    case R.id.network:
                        startActivity(new Intent(MainActivity.this, ActivityNet.class));
                        break;
                    case R.id.customizedview:
                        startActivity(new Intent(MainActivity.this, ActivityCustomizedView.class));
                        break;
                    case R.id.architecture:
                        startActivity(new Intent(MainActivity.this, ActivityArchitecture.class));
                        break;
                    case R.id.algorithm:
                        startActivity(new Intent(MainActivity.this, ActivityAlgorithm2.class));
                        break;
                    case R.id.dataStructure:
                        startActivity(new Intent(MainActivity.this, ActivityDataStructure.class));
                        break;
                    case R.id.opengl:
                        startActivity(new Intent(MainActivity.this, ActivityOpenGl.class));
                        break;
                    case R.id.componentfinterreact:
                        startActivity(new Intent(MainActivity.this, ActivityObserverPattern.class));
                        break;
                    case R.id.frame:
                        startActivity(new Intent(MainActivity.this, ActivityJavaBase.class));
                        break;
                    case R.id.login:
                        break;

                }
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        });
    }
    @OnCheckedChanged({R.id.main_settings, R.id.main_userinfo, R.id.main_searchresult, R.id.main_trending})
    public void onRadioButtonChecked(RadioButton button, boolean isChecked) {
        if (isChecked)
            onItemChecked(radioButtonlist.indexOf(button));
    }

    private void onItemChecked(int position) {
        pager_main.setCurrentItem(position);
    }

    @OnClick(R.id.login)
    void login() {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            loginFragment.setCallback(this);
        }
        if (loginFragment.getDialog() == null)
            loginFragment.show(getSupportFragmentManager(), "loginfragment");
        else loginFragment.getDialog().show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            if (x2 - x1 > 50)
                drawerLayout.openDrawer(Gravity.START);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        long nowtime = System.currentTimeMillis();
        if (nowtime - firstclicktime > 1500) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstclicktime = nowtime;
        } else {
            finish();
            System.exit(0);
        }
    }

    //baseView
    @Override
    public void showOnError(String s) {

    }

    @Override
    public void showOnLoading() {

    }

    @Override
    public void showOnSuccess() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public Context getViewContext() {
        return null;
    }

    //登陆成功以后回调
    @Override
    public void onSuccessToLogin() {
//        text_login.setText("已登陆");
//        text_login.setTextColor(Color.parseColor("#ff00ff00"));
        loginFragment.setDismissable(true);
        loginFragment.dismiss();// TODO: 2017/11/13  和下方的语句的区别
        loginFragment.getDialog().dismiss();
        startActivity(new Intent(this, ActivityAlgorithm2.class));
    }

    @Override
    public void onDismissLogin() {
        loginFragment = null;
    }

    @Override
    protected void onResume() {//防止进入其它activity之前因为mainactivity中的searchview处于获取焦点状态而导致返回以后总是弹出软键盘
        super.onResume();
        searchview_main.setFocusable(false);
        searchview_main.setFocusableInTouchMode(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES)
            searchview_main.clearFocus();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当该Activity下的子View不会处理点击事件的时候，Activity的onTouchEvent()方法才会执行
        return super.onTouchEvent(event);
    }
}
