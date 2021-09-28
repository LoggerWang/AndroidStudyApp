package com.legend.androidstudyapp.livedata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.legend.androidstudyapp.R;

import java.util.logging.Logger;

public class TestLiveDataActivity extends AppCompatActivity {
    CalModel calModel;
    TextView tvShow;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        calModel = new ViewModelProvider(this).get(CalModel.class);
        tvShow = findViewById(R.id.tvShow);
        calModel.getCal().observe(this, new Observer<Integer>(){

            @Override
            public void onChanged(Integer o) {

                Log.d("legend","onChanged= o==="+o);
                tvShow.setText(o.toString());
            }
        });

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer oldV = calModel.getCal().getValue();
                calModel.getCal().setValue(oldV+1);
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer oldV = calModel.getCal().getValue();
                calModel.getCal().setValue(oldV-1);
            }
        });
    }

}
