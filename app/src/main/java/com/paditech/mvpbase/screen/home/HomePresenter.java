package com.paditech.mvpbase.screen.home;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;

/**
 * Created by hung on 4/13/2018.
 */

public class HomePresenter extends FragmentPresenter<HomeContact.ViewOsp> implements HomeContact.PresenterViewOsp {

    @Override
    public void getAppFromApi() {
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/lastes-sale/?page=1&size=12", null, new ICallBack() {
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
                    getView().loadChild1(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("https://appsxyz.com/api/apk/apk_category/?cat_name=Action&size=60&order_by=d_rating&installs=10000&page=1", null, new ICallBack() {
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
                    getView().loadChild2(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/grossing/?page=1&size=12&installs=1000", null, new ICallBack() {
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
                    getView().loadChild3(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/gonefree/?page=1&size=12", null, new ICallBack() {
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
                    getView().loadChild4(result.getResult());

                }


                // and then callback to the UI thread

            }
        });

    }
}
