package com.legend.androidstudyapp.rxjava;

import com.legend.androidstudyapp.rxjava.model.NewsBeesBaseData;
import com.legend.androidstudyapp.rxjava.model.NewsModel;
import com.legend.androidstudyapp.rxjava.model.ResponseNewsModels;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * date：2021/10/28 11:26
 *
 * @author wanglezhi
 * desc:
 */
public interface NewsBeesApiServerice {
    @GET("mock/728/news/list")
    Observable<NewsBeesBaseData<ResponseNewsModels>> getNewsList();
}
