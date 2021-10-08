package com.legend.androidstudyapp.jetpack

import androidx.room.Database
import androidx.room.RoomDatabase
import com.legend.androidstudyapp.jetpack.room.User
import com.legend.androidstudyapp.jetpack.room.UserDao

/**
 * date：2021/9/28 16:09
 * @author wanglezhi
 * desc:
 */
@Database(entities = [User::class], version = 1)
abstract class TestDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}