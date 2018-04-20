package com.paditech.mvpbase.screen.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 4/13/2018.
 */

public class HomeActivity extends MVPActivity<HomeContact.PresenterViewOsp> implements HomeContact.ViewOsp {
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    @BindView(R.id.recycler_view_recentely)
    RecyclerView recycler_view_recentely;
    @BindView(R.id.recycler_view_on_sale)
    RecyclerView recycler_view_on_sale;
    @BindView(R.id.edit_text_search)
    TextView edit_text_search;
    @BindView(R.id.btn_see_more)
    Button btn_see_more;

    private  GridLayoutManager gridLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.act_home;
    }

    @Override
    protected void initView() {
        CommonUtil.dismissSoftKeyboard(edit_text_search,this);
        getPresenter().getAppFromApi();

        btn_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_see_more.getContext().startActivity(new Intent(btn_see_more.getContext(), ShowMoreActicity.class));
            }
        });
    }



    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return null;
    }

    @Override
    public void loadSlider() {


    }

    @Override
    public void loadChild1(List<AppModel> result) {

    }

    @Override
    public void loadChild2(List<AppModel> result) {

    }

    @Override
    public void loadChild3(List<AppModel> result) {

    }

    @Override
    public void loadChild4(List<AppModel> result) {

    }


    public void loadChildScrollListApp() {
        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(this);
        mHomeRecyclerViewAdapter.setItemNumber(6);
        recycler_view_recentely.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recycler_view_recentely.setAdapter(mHomeRecyclerViewAdapter);
        recycler_view_recentely.setNestedScrollingEnabled(false);

        gridLayoutManager = new GridLayoutManager(HomeActivity.this,2,LinearLayoutManager.HORIZONTAL,false);
        mHomeRecyclerViewAdapter.setItemNumber(12);
        recycler_view_on_sale.setLayoutManager(gridLayoutManager);
        recycler_view_on_sale.setAdapter(mHomeRecyclerViewAdapter);
        recycler_view_on_sale.setNestedScrollingEnabled(false);

    }

    @Override
    public void reloadSlider() {

    }

    @Override
    public void reloadListApp() {

    }
}
