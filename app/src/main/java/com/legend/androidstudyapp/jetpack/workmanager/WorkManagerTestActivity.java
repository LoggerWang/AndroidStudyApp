package com.legend.androidstudyapp.jetpack.workmanager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.legend.androidstudyapp.R;

import java.util.concurrent.TimeUnit;

/**
 * date：2021/12/16 17:39
 *
 * @author wanglezhi
 * desc:
 */
public class WorkManagerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workmanager);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneTimeWork();
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPeriodicWork();
            }
        });
        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDelayWork();
            }
        });
    }
    //延时性作业
    private void startDelayWork() {
        WorkRequest workRequest =
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        .setInitialDelay(15, TimeUnit.SECONDS)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(workRequest);
    }

    //重复性请求
    private void startPeriodicWork() {
        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 16, TimeUnit.MINUTES) //工作的运行时间间隔定为一小时
                        // Constraints
                        .build();
    }

    //OneTimeWorkRequest 一次性请求
    private void startOneTimeWork() {
        //对于无需额外配置的简单工作，请使用静态方法 from：
        WorkRequest workRequest = OneTimeWorkRequest.from(MyWorker.class);

        //对于更复杂的工作，可以使用构建器。
//        WorkRequest workRequest =
//                new OneTimeWorkRequest.Builder(MyWorker.class)
//                        // Additional configuration
//                        .build();

        WorkManager
                .getInstance(this)
                .enqueue(workRequest);
    }
}
