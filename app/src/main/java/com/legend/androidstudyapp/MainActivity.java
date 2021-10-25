package com.legend.androidstudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.legend.androidstudyapp.bitmap.BitmapActivity;
import com.legend.androidstudyapp.customerview.CustomerViewActivity;
import com.legend.androidstudyapp.jetpack.TestRoomActivity;
import com.legend.androidstudyapp.livedata.TestLiveDataActivity;
import com.legend.androidstudyapp.scrolltextview.ScrollTextViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}