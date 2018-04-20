package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;

import java.util.List;

/**
 * Created by hung on 4/14/2018.
 */

public class ApkFragment extends MVPFragment<ApkContact.PresenterViewOps> implements ApkContact.ViewOps {
    private static final String TAG = "No thing";

    @Override
    protected int getContentView() {
        return R.layout.fragment_apk;
    }

    @Override
    protected void initView(View view) {
        final PackageManager pm = getActivity().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages =  pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Apk file path:" + packageInfo.sourceDir);
            Log.d(TAG, "Apk file path:" + packageInfo.toString());
        }
    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return ApkPresenter.class;
    }



    private void getListApk(){

    }
}
