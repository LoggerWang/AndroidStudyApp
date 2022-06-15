package com.legend.androidstudyapp.jetpack.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * date：2021/12/16 17:42
 *
 * @author wanglezhi
 * desc:
 */
public class MyWorker extends Worker {
    public MyWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Log.d("MyWorker","MyWorker===doWork()");
        return Result.success();
    }
}
