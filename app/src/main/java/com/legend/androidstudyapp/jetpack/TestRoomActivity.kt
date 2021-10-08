package com.legend.androidstudyapp.jetpack

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.legend.androidstudyapp.R
import com.legend.androidstudyapp.jetpack.room.User

/**
 * date：2021/9/28 15:31
 * @author wanglezhi
 * desc:
 */
class TestRoomActivity :AppCompatActivity() {
    var userId=0
    var showTxt=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        var databaseBuilder: TestDataBase =
            Room.databaseBuilder(applicationContext, TestDataBase::class.java, "legend_user")
                .allowMainThreadQueries()
                .build()

        var userDao = databaseBuilder.userDao()
        findViewById<Button>(R.id.btDelete).setOnClickListener {
            var all = userDao.getAll()
            all.forEach {
                userDao.delete(it)
            }

            findViewById<TextView>(R.id.tvScreen).text = ""
        }

        findViewById<Button>(R.id.btAdd).setOnClickListener {
            userDao.insertAll(User(userId++,"legned$userId","wan$userId"))
        }

        findViewById<Button>(R.id.btAll).setOnClickListener {
            findViewById<TextView>(R.id.tvScreen).text = ""
            var all = userDao.getAll()
            all.forEach {
                showTxt = showTxt.plus(it.toString()).plus("\n")
            }
            findViewById<TextView>(R.id.tvScreen).text = showTxt
        }
    }




}