package com.paditech.mvpbase.screen.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseFragment;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.common.view.TranslationNestedScrollView;
import com.paditech.mvpbase.screen.detail.DetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hung on 1/2/2018.
 */

public class HomeFragment extends BaseFragment {

    HomeViewPagerAdapter mHomeViewPagerAdapter;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    @BindView(R.id.view_pager_home)
    ViewPager viewPagerHome;
    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;
    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.scrollView_home)
    TranslationNestedScrollView scrollView;
    @BindView(R.id.linearlauout_home)
    LinearLayout linearlauout_home;
    Activity act;
    private List<AppModel.SourceBean> mList = new ArrayList<>();
    private boolean mRunned;

    public static HomeFragment getInstance(Activity act) {
        HomeFragment f = new HomeFragment();
        f.setAct(act);
        return f;

    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
       scrollView.setViewPager(viewPagerHome, CommonUtil.getWidthScreen(getActivityReference()) / 2);
        scrollView.setViewGroupBottom(linearlauout_home);
        setViewpager();
        setRecyclerViewAdapter();
        if (!mRunned) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        }
        mRunned = true;
    }

    public void setViewpager() {
        if (mList == null || mList.isEmpty()) {

            mHomeViewPagerAdapter = new HomeViewPagerAdapter();
            getPagerData();
        }
        viewPagerHome.setAdapter(mHomeViewPagerAdapter);
        circleIndicator.setViewPager(viewPagerHome);


    }

    public void setRecyclerViewAdapter() {
        if (mHomeRecyclerViewAdapter == null) {
            mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getAct());
            getFeedData1("http://appsxyz.com/api/apk/lastes-sale/?page=1&size=6");
            getFeedData2("http://appsxyz.com/api/apk/topdownload/?page=1&size=6");
            getFeedData3("http://appsxyz.com/api/apk/grossing/?page=1&size=6&installs=1000");
            getFeedData4("http://appsxyz.com/api/apk/gonefree/?page=1&size=6");
        }
        recyclerViewHome.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHome.setAdapter(mHomeRecyclerViewAdapter);
        recyclerViewHome.setNestedScrollingEnabled(false);


    }

    private void getPagerData() {
        String url = "http://appsxyz.com/api/apk/slider/?page=1&size=5&installs=1000";
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                System.out.println(response);
                Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {

                    for (AppModel app : result.getResult()) {
                        AppModel.SourceBean newApp = new AppModel.SourceBean();
                        newApp = app.getSource();
                        mList.add(newApp);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHomeViewPagerAdapter.setList(mList);
                            mHomeViewPagerAdapter.setActivityName("HOME");
                            circleIndicator.setViewPager(viewPagerHome);
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });
    }

    public class MyTimerTask extends TimerTask {

        @Override

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (viewPagerHome.getCurrentItem() != (mList.size() - 1))
                            viewPagerHome.setCurrentItem(viewPagerHome.getCurrentItem() + 1);
                        else
                            viewPagerHome.setCurrentItem(0);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });

        }
    }

    private void getFeedData1(String url) {

        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                mHomeRecyclerViewAdapter.setmList1(result.getResult());
                        }
                    });

                }
            }
        });
    }

    private void getFeedData2(String url) {
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                mHomeRecyclerViewAdapter.setmList2(result.getResult());
                        }
                    });

                }
            }
        });
    }

    private void getFeedData3(String url) {
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                mHomeRecyclerViewAdapter.setmList3(result.getResult());
                        }
                    });

                }
            }
        });
    }

    private void getFeedData4(String url) {
        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                mHomeRecyclerViewAdapter.setmList4(result.getResult());
                        }
                    });

                }
            }
        });
    }


}
