package com.paditech.mvpbase.screen.main;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 4/17/2018.
 */

public interface HomeActContact {
    interface ViewOps extends ActivityViewOps{
        void setSearchResult(List<AppModel> listApp);
    }

    interface PresenterViewOps extends ActivityPresenterViewOps{
        void cURLSearchData(String query);
    }


}


