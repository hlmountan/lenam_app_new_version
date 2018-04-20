package com.paditech.mvpbase.screen.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseFragment;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.LoadMoreRecyclerView;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by Nha Nha on 1/2/2018.
 */

public class SearchFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {


    @BindView(R.id.recycler_view_search_item)
    LoadMoreRecyclerView recyclerView;
    HomeListAppAdapter mHomeListAppAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public String getmAppAPI() {
        return mAppAPI;
    }

    public void setmAppAPI(String mAppAPI) {
        this.mAppAPI = mAppAPI;
    }

    private String mAppAPI;

    private int mPage = 1;

    @Override
    protected int getContentView() {
        return R.layout.frag_search;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View view) {
        CommonUtil.dismissSoftKeyboard(view, getActivityReference());
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mHomeListAppAdapter = new HomeListAppAdapter(getActivityReference());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(mHomeListAppAdapter);
        recyclerView.setLoadMoreListener(this);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);

                if (!recyclerView1.canScrollVertically(1)) {
                    if (!recyclerView.ismIsLoading()) {
                        recyclerView.showLoadMoreIndicator();
                        onLoadMore();
                    }
                }else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSearchResult(String key) {
        String url = "https://appsxyz.com/api/apk/search/?q=" + key + "&page=%s&size=15";
        if (!key.equals(""))
            progressBar.setVisibility(View.VISIBLE);

        mPage = 1;
        setmAppAPI(url);
        getMoreApp();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        mPage++;
        getMoreApp();
        System.out.println(" load more ");

    }

    private void getMoreApp() {
        APIClient.getInstance().execGet(String.format(mAppAPI, mPage), null, new ICallBack() {
            @Override
            public void onErrorToken() {
            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(final String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                if (mPage == 1) {
                                    mHomeListAppAdapter.setmList(result.getResult());
                                } else {
                                    mHomeListAppAdapter.addMoremList(result.getResult());
                                }
                            swipeRefreshLayout.setRefreshing(false);
                            recyclerView.onLoadMoreComplete();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    // end of data then turn off progressbar
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                }

            }
        });
    }
}
