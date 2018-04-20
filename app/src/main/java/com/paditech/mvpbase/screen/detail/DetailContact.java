package com.paditech.mvpbase.screen.detail;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.ArrayList;

/**
 * Created by hung on 4/13/2018.
 */

public interface DetailContact {
    interface ViewOps extends ActivityViewOps{
        void setAppInfo(AppModel.SourceBean app);
        void setAppPriceHistory(ArrayList<ArrayList<String>> priceHistory);
        void setRelateApp();
        void setPriceHistory();
        void setDevApp();

    }

    interface PresenterViewOps extends ActivityPresenterViewOps{
        void cURLFromApi(String appid,int isHistory);
    }
}
