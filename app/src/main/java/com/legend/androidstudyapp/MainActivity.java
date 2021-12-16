package com.legend.androidstudyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.legend.androidstudyapp.bitmap.BitmapActivity;
import com.legend.androidstudyapp.customerview.CustomerViewActivity;
import com.legend.androidstudyapp.jetpack.TestRoomActivity;
import com.legend.androidstudyapp.livedata.TestLiveDataActivity;
import com.legend.androidstudyapp.rxjava.RxjavaActivity;
import com.legend.androidstudyapp.scrolltextview.ScrollTextViewActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

public class MainActivity extends AppCompatActivity {

    private ActivityManager activityManager;
    private PackageManager packageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("legend===================");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("==============aaaa");
            }
        },1000,1000);
        activityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        packageManager = getPackageManager();
        setContentView(R.layout.activity_main);
        ImageView ivTest = findViewById(R.id.ivTest);
        findViewById(R.id.btCustomView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CustomerViewActivity.class));
            }
        });
        findViewById(R.id.btCompose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ComposeActivity.class));
                Drawable d = tintDrawable(getResources().getDrawable(R.drawable.ic_home_light),ColorStateList.valueOf(0xff0000));
                ivTest.setBackground(d);

                changeLuncher("com.legend.androidstudyapp.newsLuncherActivity");
            }
        });
        findViewById(R.id.btTestLeakCanary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestLeakCanaryActivity.class));
            }
        });
        findViewById(R.id.btRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestRoomActivity.class));
            }
        });
        findViewById(R.id.ARouter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/testt/testActivity").navigation();
            }
        });
        findViewById(R.id.btBitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, BitmapActivity.class));
            }
        });
        findViewById(R.id.btScrollTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ScrollTextViewActivity.class));
            }
        });
        findViewById(R.id.btRxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
            }
        });
    }

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    private void setDrawable(Button button, Drawable drawable, int color) {
        drawable = tintDrawable(drawable, ColorStateList.valueOf(color));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int size;
        size = 90 * dm.heightPixels / 2560;
        drawable.setBounds(0, 0, size, size);
        button.setCompoundDrawables(null, drawable, null, null);
        button.setTextColor(color);
    }

    private boolean setIcon = true;

    private void changeLuncher(String name) {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(getComponentName(),
                COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(MainActivity.this, name),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        //Intent 重启 Launcher 应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
/*
        if (".".contentEquals(getTitle())) {
            setIcon = false;
        } else {
            setIcon = true;
        }

        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.legend.androidstudyapp.MainActivity"),
                setIcon == true ? COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.legend.androidstudyapp.changeAfter"),
                setIcon == true ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/

//        packageManager.setComponentEnabledSetting(getComponentName(),
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
    }



}