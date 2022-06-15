package com.legend.androidstudyapp.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.legend.androidstudyapp.R;
import com.legend.androidstudyapp.WebViewCommentActivity;

/**
 * date：2021/12/31 16:11
 *
 * @author wanglezhi
 * desc:
 */
public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button button1 = new Button(this);
        button1.setText("普通通知");
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 180);
        button1.setLayoutParams(layoutParams1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotify();
            }
        });
        linearLayout.addView(button1);
        Button button2 = new Button(this);
        button2.setText("常驻通知");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 180);
        button2.setLayoutParams(layoutParams2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongNotify();
            }
        });
        linearLayout.addView(button2);
        createNotificationChannel();
        setContentView(linearLayout);
    }

    private void showLongNotify() {
        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.item_switcher_view);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.item_switcher_view);

        // Apply the layouts to the notification
        Notification customNotification = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.drawable.girl_4)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setOngoing(true)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(101,customNotification);

    }

    private void showNotify() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, WebViewCommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.girl_4)
                .setContentTitle("我的通知标题")
                .setContentText("通知内容")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //点击跳转的意图
                .setContentIntent(pendingIntent)
                //用户点按通知后自动移除通知
//                .setAutoCancel(true)
                // 设置为public后，通知栏将在锁屏界面显示
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                //设置为常驻
                .setOngoing(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(100, builder.build());


    }

    //必须先通过向 createNotificationChannel() 传递 NotificationChannel 的实例在系统中注册应用的通知渠道，然后才能在 Android 8.0 及更高版本上提供通知。
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
