package com.legend.androidstudyapp.livedata;

import androidx.lifecycle.LiveData;

/**
 * @desc:
 * @author: wanglezhi
 * @createTime: 2022/6/27 11:20 上午
 */
public class CustomLiveData extends LiveData<String> {

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
