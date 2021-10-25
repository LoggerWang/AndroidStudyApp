package com.legend.androidstudyapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * date：2021/10/12 15:37
 *
 * @author wanglezhi
 * desc:
 */
@Route(path = "/testt/testActivity")
public class TestArouterActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_test);
    }
}
