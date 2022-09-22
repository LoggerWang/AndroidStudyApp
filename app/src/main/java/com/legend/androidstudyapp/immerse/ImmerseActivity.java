package com.legend.androidstudyapp.immerse;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.legend.androidstudyapp.R;

/**
 * @desc:  详细参考郭林 公众号文章 链接如下
 *  https://mp.weixin.qq.com/s/AiCNJAi9CgYDE1UuzCboGg
 *  实现沉浸式状态栏最基本步骤：
 *  1、设置透明状态栏： Activity中setContentView()之后 设置 getWindow().setStatusBarColor(Color.TRANSPARENT);
 *  2、根布局用<CoordinatorLayout>,  而<ConstraintLayout>、<LinearLayout>、<RelativeLayout>、<FrameLayout>都不可以
 *  3、在根布局增加属性 ：android:fitsSystemWindows="true"
 *  4、如果想让图片实现沉浸式，需要在<ImageView>标签外用<CollapsingToolbarLayout>包裹,且这两个标签都要加属性 ：android:fitsSystemWindows="true"
 *
 *  属性参考 https://blog.csdn.net/weixin_45882303/article/details/121155068
 * @author: wanglezhi
 * @createTime: 2022/6/15 3:39 下午
 */
public class ImmerseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immerse);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置透明状态栏
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏即隐藏状态栏

        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1://设置状态栏为白底黑字
                // 显示状态栏 可去掉
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏背景颜色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

                break;
            case R.id.bt2://设置状态栏为黑底白字
                // 显示状态栏 可去掉
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));//设置状态栏背景颜色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

                break;
            case R.id.bt3:
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色


                break;
        }
    }
}
