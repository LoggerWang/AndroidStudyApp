package com.legend.androidstudyapp.audio;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.legend.androidstudyapp.BuildConfig;
import com.legend.androidstudyapp.R;
import com.legend.androidstudyapp.audio.audioa.calculators.AudioCalculator;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import be.tarsos.dsp.util.Complex;
import be.tarsos.dsp.util.fft.FFT;

/**
 * @desc:
 * @author: wanglezhi
 * @createTime: 2022/11/22 3:32 下午
 */
public class AudioActivity extends AppCompatActivity implements Runnable {

    private Button mBtnStartRecord,mBtnStopRecord;
    //指定音频源 这个和MediaRecorder是相同的 MediaRecorder.AudioSource.MIC指的是麦克风
    private static final int mAudioSource = MediaRecorder.AudioSource.MIC;
    //指定采样率 （MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。设置采样率为44100，目前为常用的采样率，官方文档表示这个值可以兼容所有的设置）
    private static final int mSampleRateInHz=44100 ;
    //指定捕获音频的声道数目。在AudioFormat类中指定用于此的常量
    private static final int mChannelConfig= AudioFormat.CHANNEL_CONFIGURATION_MONO; //单声道
    //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，它实际上是原始音频样本。
    //因此可以设置每个样本的分辨率为16位或者8位，16位将占用更多的空间和处理能力,表示的音频也更加接近真实。
    private static final int mAudioFormat=AudioFormat.ENCODING_PCM_16BIT;
    //指定缓冲区大小。调用AudioRecord类的getMinBufferSize方法可以获得。
    private int mBufferSizeInBytes;
    private File mRecordingFile;//储存AudioRecord录下来的文件
    private boolean isRecording = false; //true表示正在录音
    private AudioRecord mAudioRecord=null;
    private File mFileRoot=null;//文件目录
    //存放的目录路径名称
    private   String mPathName ;
    //保存的音频文件名
    private static final String mFileName = "audiorecordtest.pcm";
    //缓冲区中数据写入到数据，因为需要使用IO操作，因此读取数据的过程应该在子线程中执行。
    private Thread mThread;
    private DataOutputStream mDataOutputStream;
    AudioCalculator audioCalculator;
    TextView tvFrq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tvFrq = findViewById(R.id.tvFrq);
        mPathName = getFilesDir()+ "/audiioRecordtest";
         audioCalculator =  new AudioCalculator();
        initDatas();
        initUI();
    }


    //初始化数据
    private void initDatas() {
        if (ContextCompat.checkSelfPermission(AudioActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AudioActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1234);
            return;
        }
        mBufferSizeInBytes = AudioRecord.getMinBufferSize(mSampleRateInHz,mChannelConfig, mAudioFormat);//计算最小缓冲区
        mAudioRecord = new AudioRecord(mAudioSource,mSampleRateInHz,mChannelConfig,
                mAudioFormat, mBufferSizeInBytes);//创建AudioRecorder对象
        mFileRoot = new File(mPathName);
        if(!mFileRoot.exists())
            mFileRoot.mkdirs();//创建文件夹
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1234: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initDatas();

                } else {
                    Log.d("TAG", "permission denied by user");
                }
                return;
            }
        }
    }
    //初始化UI
    private void initUI() {
        mBtnStartRecord = findViewById(R.id.btn_start_record);
        mBtnStopRecord = findViewById(R.id.btn_stop_record);
        mBtnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });
        mBtnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
        mBtnStopRecord.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyApp();
            }
        },8000);
        tvFrq.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvFrq.setText("888");
            }
        },2000);

    }
    //开始录音
    public void startRecord() {
        //AudioRecord.getMinBufferSize的参数是否支持当前的硬件设备
        if (AudioRecord.ERROR_BAD_VALUE == mBufferSizeInBytes || AudioRecord.ERROR == mBufferSizeInBytes) {
            throw new RuntimeException("Unable to getMinBufferSize");
        }else{
            destroyThread();
            isRecording = true;
            if(mThread == null){
                mThread = new Thread(this);
                mThread.start();//开启线程
            }
        }
    }
    /**
     * 销毁线程方法
     */
    private void destroyThread() {
        try {
            isRecording = false;
            if (null != mThread && Thread.State.RUNNABLE == mThread.getState()) {
                try {
                    Thread.sleep(500);
                    mThread.interrupt();
                } catch (Exception e) {
                    mThread = null;
                }
            }
            mThread = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mThread = null;
        }
    }
    //停止录音
    public void stopRecord() {
        isRecording = false;
        //停止录音，回收AudioRecord对象，释放内存
        if (mAudioRecord != null) {
            if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {//初始化成功
                mAudioRecord.stop();
            }
            if (mAudioRecord  !=null ) {
                mAudioRecord.release();
            }
        }
    }
    @Override
    public void run() {
        //标记为开始采集状态
        isRecording = true;
        //创建一个流，存放从AudioRecord读取的数据
        mRecordingFile = new File(mFileRoot,mFileName);
        if(mRecordingFile.exists()){//音频文件保存过了删除
            mRecordingFile.delete();
        }
        try {
            mRecordingFile.createNewFile();//创建新文件
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("lu","创建储存音频文件出错");
        }
        try {
            //获取到文件的数据流
            mDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(mRecordingFile)));
            byte[] buffer = new byte[mBufferSizeInBytes];
            //判断AudioRecord未初始化，停止录音的时候释放了，状态就为STATE_UNINITIALIZED
            if(mAudioRecord.getState() == mAudioRecord.STATE_UNINITIALIZED){
                initDatas();
            }
            mAudioRecord.startRecording();//开始录音
            //getRecordingState获取当前AudioReroding是否正在采集数据的状态
            while (isRecording && mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                int bufferReadResult = mAudioRecord.read(buffer,0,mBufferSizeInBytes);
                audioCalculator.setBytes(buffer);
                double frequency = audioCalculator.getFrequency();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvFrq.setText(String.valueOf(frequency));
                        if (frequency>400 && !isForeground(getApplicationContext())) {
                            Log.d("频率==","====frequency===切到前台");
//                            setTopApp(AudioActivity.this);
//                            notifyApp();
                        }
                    }
                });

                Log.d("频率==","====frequency==="+frequency+"==="+isForeground(getApplicationContext()));
                for (int i = 0; i < bufferReadResult; i++)
                {
                    mDataOutputStream.write(buffer[i]);
                }
            }
            mDataOutputStream.close();
        } catch (Throwable t) {
            Log.e("lu", "Recording Failed"+t.toString());
            stopRecord();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyThread();
        stopRecord();
    }

//    public void calculate() {
//
//        double[] magnitude = new double[mBufferSizeInBytes / 2];
//
//        //Create Complex array for use in FFT
//        Complex[] fftTempArray = new Complex[mBufferSizeInBytes];
//        for (int i = 0; i < mBufferSizeInBytes; i++) {
//            fftTempArray[i] = new Complex(buffer[i], 0);
//        }
//
//        //Obtain array of FFT data
//        final Complex[] fftArray = FFT.fft(fftTempArray);
//        // calculate power spectrum (magnitude) values from fft[]
//        for (int i = 0; i < (mBufferSizeInBytes / 2) - 1; ++i) {
//
//            double real = fftArray[i].re();
//            double imaginary = fftArray[i].im();
//            magnitude[i] = Math.sqrt(real * real + imaginary * imaginary);
//
//        }
//
//        // find largest peak in power spectrum
//        double max_magnitude = magnitude[0];
//        int max_index = 0;
//        for (int i = 0; i < magnitude.length; ++i) {
//            if (magnitude[i] > max_magnitude) {
//                max_magnitude = (int) magnitude[i];
//                max_index = i;
//            }
//        }
//        double freq = 44100 * max_index / mBufferSizeInBytes;//here will get frequency in hz like(17000,18000..etc)
//
//    }

    /**
     * 当前应用是否处于前台
     */
    private static boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            if (processes != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                    if (processInfo.processName.equals(context.getPackageName())) {
                        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            //这个处于前台的进程是否是我们的进程
                            return processInfo.processName.equals(BuildConfig.APPLICATION_ID);
//                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param context
     */
    public static void bring2Front(Context context) {
        /**获取ActivityManager*/
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> list = activityManager.getAppTasks();
            for (ActivityManager.AppTask appTask : list){
                appTask.moveToFront();
                break;
            }
        }else {
            /**获得当前运行的task(任务)*/
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                /**找到本应用的 task，并将它切换到前台*/
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(taskInfo.id, 0);
                    break;
                }
            }
        }
    }

    public void notifyApp(){
//        Intent intent = new Intent(this, AudioActivity.class);
//        intent.setAction(Intent.ACTION_VIEW);
////        intent.addCategory(Intent.CATEGORY_LAUNCHER);
////        intent.setAction(Intent.ACTION_MAIN);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        startActivity(intent);
        Log.d("legend","==notifyApp====");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mytest://hello.world:1024/test?name=zhangsan&age=27");
        intent.setData(data);
        this.startActivity(intent);

//        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
//        for (int i = 0; i < runningTasks.size(); i++) {
//            if (getPackageName().equals(runningTasks.get(i).topActivity.getPackageName())) {
//                activityManager.moveTaskToFront(runningTasks.get(i).id,0);
//
//                return;
//            }
//        }
    }

    //当本应用位于后台时，则将它切换到最前端
    public static void setTopApp(Context context) {
        if (isRunningForeground(context)) {
            return;
        }
        //获取ActivityManager
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        //获得当前运行的task(任务)
        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
        Log.d("频率==","====*******===切到前台=="+taskInfoList.size());
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            //找到本应用的 task，并将它切换到前台
            if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                activityManager.moveTaskToFront(taskInfo.id, 0);
                break;
            }
        }

    }

    //判断本应用是否已经位于最前端：已经位于最前端时，返回 true；否则返回 false
    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                return true;
            }
        }
        return false;
    }

}
