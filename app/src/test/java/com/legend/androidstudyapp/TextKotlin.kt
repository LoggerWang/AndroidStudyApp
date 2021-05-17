package com.legend.androidstudyapp

import com.legend.androidstudyapp.datastruct.SingleHungry
import com.legend.androidstudyapp.datastruct.SingleHungrySafeDoubleLock
import org.junit.Test

/**
 * @author wanglezhi
 * @date   2021/5/14 16:07
 * @discription
 */
class TextKotlin {
   @Test
    fun testSingHungry(){
        var instance = SingleHungry.getA()
        instance.printM()

       var instance1 = SingleHungrySafeDoubleLock.instance
       instance1.printM()
   }
}