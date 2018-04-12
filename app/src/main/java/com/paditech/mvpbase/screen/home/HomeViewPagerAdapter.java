package com.paditech.mvpbase.screen.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.detail.DetailActivity;
import com.paditech.mvpbase.screen.detail.ScreenShotFullScreenActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 1/2/2018.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private List<AppModel.SourceBean> mList;
    private String activityName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override

    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView mImageView = new ImageView(container.getContext());
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageUtil.loadImage(container.getContext(), mList.get(position).getCover(), mImageView);
        container.addView(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (getActivityName()) {
                    case "HOME":
                        EventBus.getDefault().postSticky(mList.get(position));
                        intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("is_cover",true);
                        mImageView.getContext().startActivity(intent);
                        break;
                    default:
                        EventBus.getDefault().postSticky(mList);
                        intent = new Intent(view.getContext(), ScreenShotFullScreenActivity.class);
                        mImageView.getContext().startActivity(intent);
                        break;
                }

            }

        });

        return mImageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
