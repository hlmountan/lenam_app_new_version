package com.paditech.mvpbase.screen.main;

import android.view.View;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;

/**
 * Created by hung on 4/17/2018.
 */

public class HomeActPresenter extends ActivityPresenter<HomeActContact.ViewOps> implements HomeActContact.PresenterViewOps {
    @Override
    public void cURLSearchData(String query) {
        APIClient.getInstance().execGet("https://appsxyz.com/api/apk/search/?q=" + query + "&page=1&size=15", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().setSearchResult(result.getResult());
                }
            }
        });
    }
}
