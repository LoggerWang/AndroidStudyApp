package com.legend.androidstudyapp.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * date：2021/9/26 20:04
 * @author wanglezhi
 * desc:
 */
class CalModel:ViewModel() {
    //lazy 的 lambda 表达式中的所有代码都会被执行一次且只会执行一次。
    private val calLiveData by lazy { MutableLiveData<Int>()}

    private lateinit var liveData2: MutableLiveData<Int>

    fun getLiveData2(): MutableLiveData<Int>{
        if (liveData2==null) {
            liveData2 = MutableLiveData<Int>()
        }
        return liveData2
    }

   fun getCal():MutableLiveData<Int>{
       if (calLiveData.value==null) {
           calLiveData.value = 0
       }
       return calLiveData
   }
}