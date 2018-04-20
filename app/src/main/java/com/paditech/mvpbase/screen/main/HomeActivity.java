package com.paditech.mvpbase.screen.main;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends MVPActivity<HomeActContact.PresenterViewOps> implements HomeActContact.ViewOps, ViewPager.OnPageChangeListener, View.OnFocusChangeListener, View.OnClickListener {

    @BindView(R.id.vp_tablayout)
    ViewPager viewPager_tab_layout;
    @BindView(R.id.edit_text_search)
    EditText edit_text_search;
    @BindView(R.id.notification_view)
    LinearLayout notification_view;

    @BindView(R.id.search_view)
    LinearLayout search_view;
    @BindView(R.id.btn_cancel_search)
    Button btn_cancel_search;
    @BindView(R.id.btn_assivetouch)
    FloatingActionButton btn_assivetouch;

    @BindView(R.id.ab_search)
    AppBarLayout ab_search;


    @BindView(R.id.recycler_view_search_item)
    RecyclerView recycler_view_search_item;
    HomeListAppAdapter mHomeListAppAdapter;
    NotificationRecycleViewAdapter mNotificationRecycleViewAdapter;

    @BindView(R.id.recycler_view_notification)
    RecyclerView recycler_view_notification;

    @BindView(R.id.btn_notification)
    Button btn_notification;
    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setupViewPagerMain();
        mHomeListAppAdapter = new HomeListAppAdapter(this);
        mNotificationRecycleViewAdapter = new NotificationRecycleViewAdapter();
        recycler_view_notification.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view_notification.setAdapter(mNotificationRecycleViewAdapter);

        recycler_view_search_item.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view_search_item.setAdapter(mHomeListAppAdapter);
        CommonUtil.dismissSoftKeyboard(findViewById(R.id.drawer), this);
        edit_text_search.setOnFocusChangeListener(this);
        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getPresenter().cURLSearchData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_cancel_search.setOnClickListener(this);
        btn_assivetouch.setOnClickListener(this);
        viewPager_tab_layout.addOnPageChangeListener(this);
        btn_notification.setOnClickListener(this);

    }

    private void setupViewPagerMain() {
        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mMainViewPagerAdapter = new MainViewPagerAdapter(manager);
        mMainViewPagerAdapter.setAct(this);
        viewPager_tab_layout.setAdapter(mMainViewPagerAdapter);
        viewPager_tab_layout.setOffscreenPageLimit(2);

    }

    private void getSearchData(final String key) {

        EventBus.getDefault().post(key);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != 0) {
            btn_assivetouch.setImageResource(R.drawable.ic_home);
        } else {
            btn_assivetouch.setImageResource(R.drawable.pulse);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_search:
                search_view.setVisibility(View.GONE);
                viewPager_tab_layout.setVisibility(View.VISIBLE);
                btn_cancel_search.setVisibility(View.GONE);
                notification_view.setVisibility(View.GONE);
                btn_notification.setVisibility(View.VISIBLE);
                // clear text when cancel search
                edit_text_search.setText("");
                break;
            case R.id.btn_assivetouch:
                if (viewPager_tab_layout.getCurrentItem() == 0) {
                    EventBus.getDefault().post(new ScrollTopEvent());
                    ab_search.setExpanded(true);
                } else {
                    viewPager_tab_layout.setCurrentItem(0);
                }
                if (viewPager_tab_layout.getVisibility() == View.GONE){
                    viewPager_tab_layout.setVisibility(View.VISIBLE);
                    notification_view.setVisibility(View.GONE);
                    search_view.setVisibility(View.GONE);
                    btn_notification.setVisibility(View.VISIBLE);
                    btn_cancel_search.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_notification:
                viewPager_tab_layout.setVisibility(View.GONE);
                notification_view.setVisibility(View.VISIBLE);
                btn_assivetouch.setImageResource(R.drawable.ic_home);

                break;
        }
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            search_view.setVisibility(View.VISIBLE);
            viewPager_tab_layout.setVisibility(View.GONE);
            btn_cancel_search.setVisibility(View.VISIBLE);
            notification_view.setVisibility(View.GONE);
            btn_notification.setVisibility(View.GONE);

        }
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return HomeActPresenter.class;
    }

    @Override
    public void setSearchResult(final List<AppModel> listApp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList(listApp);
            }
        });


    }
}
