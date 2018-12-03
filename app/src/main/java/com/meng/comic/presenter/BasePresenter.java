package com.meng.comic.presenter;

import android.app.Activity;

import com.meng.comic.view.IBaseView;

import java.util.Date;

/**
 * Created by Dmeng on 2018/11/20.
 */

public class BasePresenter<GV extends IBaseView> {

    protected GV mView;
    protected Activity mContext;

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }

    protected BasePresenter() {
    }

    public long getCurrentTime() {
        Date date = new Date();
        long dataTime = date.getTime();
        return dataTime;
    }
}
