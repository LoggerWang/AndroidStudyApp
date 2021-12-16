package com.legend.androidstudyapp.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.legend.androidstudyapp.R;
import com.legend.androidstudyapp.rxjava.model.NewsBeesBaseData;
import com.legend.androidstudyapp.rxjava.model.ResponseNewsModels;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date：2021/10/27 17:39
 *
 * @author wanglezhi
 * desc:
 */
public class RxjavaActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                test1();
                break;
            case R.id.bt2:
                testInterval();
                break;
        }
    }

    Disposable disposable;
    private void testInterval() {
        Observable.interval(1,2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://yapi.ushareit.me/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                        NewsBeesApiServerice newsBeesApiServerice = retrofit.create(NewsBeesApiServerice.class);
                        Observable<NewsBeesBaseData<ResponseNewsModels>> newsListObservable = newsBeesApiServerice.getNewsList();
                        newsListObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<NewsBeesBaseData<ResponseNewsModels>>() {
                                    @Override
                                    public void onSubscribe(@NotNull Disposable d) {

                                        Log.d(TAG, "获取列表的onSubscribe()===");
                                    }

                                    @Override
                                    public void onNext(@NotNull NewsBeesBaseData<ResponseNewsModels> responseNewsModelsNewsBeesBaseData) {
                                        ResponseNewsModels data = responseNewsModelsNewsBeesBaseData.getData();
                                        Log.d(TAG, "获取列表的onNext()==="+data.getNewsModels().get(0).getTitle());

                                    }

                                    @Override
                                    public void onError(@NotNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                        Log.d(TAG, "获取列表的onComplete()===");
                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                disposable = d;
                Log.d(TAG, "观察者Observer的onSubscribe()最先调用");
            }

            @Override
            public void onNext(@NotNull Long aLong) {

                Log.d(TAG, "观察者Observer的onNext()==="+aLong);
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

                Log.d(TAG, "观察者Observer的onComplete()===");
            }
        });
    }

    private void test1() {
        // 步骤1：创建被观察者 Observable & 生产事件
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable的 subscribe()");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        });
        // 步骤2：创建观察者 Observer 并 定义响应事件行为
        Observer<Integer> integerObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                // 默认最先调用复写的 onSubscribe（）
                Log.d(TAG, "观察者Observer的onSubscribe()最先调用");
            }

            @Override
            public void onNext(@NotNull Integer value) {
                Log.d(TAG, "对Next事件"+ value +"作出响应"  );
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
        // 步骤3：通过订阅（subscribe）连接观察者和被观察者
        integerObservable.subscribe(integerObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null) {
            disposable.dispose();
        }
    }
}
