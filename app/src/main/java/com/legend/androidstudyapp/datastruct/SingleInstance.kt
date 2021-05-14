package com.legend.androidstudyapp.datastruct

import android.util.Log


/**
 * @author wanglezhi
 * @date   2021/5/14 15:56
 * @discription 单例
 */
const val TAG = "Single=="

/**
 * 饿汉式
 */
object SingleIns{
    var name = "legend"

    fun printM(){
       Log.d(TAG,"kotlin 单例 饿汉式")
    }
}

/**
 * 懒汉式-线程不安全
 */
class SingleHungry{
    private constructor()
    companion object{
        private var instance: SingleHungry?=null
        get() {
            if (field==null) {
                field = SingleHungry()
            }
            return field
        }

        fun getA(): SingleHungry {
            return instance!!
        }
    }

    fun printM(){
        Log.d(TAG,"kotlin 单例 懒汉式 线程不安全")
    }
}

/**
 * 懒汉式-线程安全
 */
class SingleHungrySafe{
    private constructor()
    companion object{
        private var instance: SingleHungrySafe?=null
        get() {
            if (field==null) {
                field = SingleHungrySafe()
            }
            return field
        }

        @Synchronized
        fun getA(): SingleHungrySafe {
            return instance!!
        }
    }

    fun printM(){
        Log.d(TAG,"kotlin 单例 懒汉式 线程安全")
    }
}

/**
 * 懒汉式-线程安全
 */
class SingleHungrySafeDoubleLock private constructor(){
    companion object{
         val instance: SingleHungrySafeDoubleLock by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
             SingleHungrySafeDoubleLock()
         }
    }

    fun printM(){
        Log.d(TAG,"kotlin 单例 懒汉式 线程安全")
    }
}

class TestSingle{


    fun testSingHungry(){
        var instance = SingleHungry.getA()
    }

}
