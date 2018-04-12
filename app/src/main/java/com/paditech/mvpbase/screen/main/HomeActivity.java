package com.paditech.mvpbase.screen.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_tablayout)
    ViewPager viewPager_tab_layout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_tablayout_title)
    TextView textView;
    @BindView(R.id.search_bar)
    android.support.v7.widget.SearchView searchView;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    private int[] navLabels = {
            R.string.title_home,
            R.string.title_category,
            R.string.title_search
    };
    private int[] navIcons = {
            R.drawable.ic_dashboard_18dp,
            R.drawable.ic_category_18dp,
            R.drawable.ic_search_18dp
    };
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setupNavigationMenu();
        setupViewPagerMain();
        textView.setText("Furniture");
        searchView.setVisibility(View.GONE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        getSupportActionBar().setDisplayUseLogoEnabled(true);
                        getSupportActionBar().setHomeButtonEnabled(true);
                        textView.setText("Furniture");
                        searchView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        getSupportActionBar().setDisplayUseLogoEnabled(false);
                        getSupportActionBar().setHomeButtonEnabled(false);
                        textView.setText("Category");
                        searchView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        getSupportActionBar().setDisplayUseLogoEnabled(false);
                        getSupportActionBar().setHomeButtonEnabled(false);
                        textView.setVisibility(View.GONE);
                        searchView.setVisibility(View.VISIBLE);
                        searchView.setQueryHint("Search App");
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {

                }

            }
        });
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dashboard_18dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_category_18dp);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search_18dp);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchData(newText);
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.onActionViewExpanded();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((mActionBarDrawerToggle.onOptionsItemSelected(item))) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setupViewPagerMain() {
        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mMainViewPagerAdapter = new MainViewPagerAdapter(manager);
        mMainViewPagerAdapter.setAct(this);
        viewPager_tab_layout.setAdapter(mMainViewPagerAdapter);
        viewPager_tab_layout.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager_tab_layout);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.navigation_tablayout, null);

            // get child TextView and ImageView from this layout for the icon and label
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_label.setText(getResources().getString(navLabels[i]));
            tab_icon.setImageResource(navIcons[i]);

            // finally publish this custom view to navigation tab
            tabLayout.getTabAt(i).setCustomView(tab);
        }
        viewPager_tab_layout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setupNavigationMenu() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        textView.setText(" ");

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getSearchData(final String key) {

        EventBus.getDefault().post(key);
    }

}
