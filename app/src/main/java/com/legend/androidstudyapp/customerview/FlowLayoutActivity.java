package com.legend.androidstudyapp.customerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.legend.androidstudyapp.R;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;

import java.util.ArrayList;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @desc:
 * @author: wanglezhi
 * @createTime: 2022/7/18 4:00 下午
 */
public class FlowLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);

//        //设置布局管理器
//        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(FlowLayoutActivity.this);
//        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
//        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
//        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
//        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
//        //justifyContent 属性定义了项目在主轴上的对齐方式。
//        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
//
//        mRecyclerView.setLayoutManager(flexboxLayoutManager);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i==2) {
                strings.add("asdgewrgasrgssdggggggggg"+i);
            }else if(i==6){
                strings.add("wwwwwwwwww"+i);
            }else {

                strings.add("text"+i);
            }



        }

        FlowViewGroup flowviewgroup = findViewById(R.id.flowviewgroup);
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("https://img0.baidu.com/it/u=2471949583,3673126699&fm=253&fmt=auto&app=120&f=JPEG?w=640&h=640");
        urlList.add("https://img2.baidu.com/it/u=3550180748,3323046396&fm=253&fmt=auto&app=138&f=JPEG?w=494&h=500");
        urlList.add("https://img1.baidu.com/it/u=2319001023,3862119530&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400");
        urlList.add("https://img0.baidu.com/it/u=998893270,2846374784&fm=253&fmt=auto&app=138&f=JPEG?w=510&h=500");
        urlList.add("https://img1.baidu.com/it/u=3315604911,484903301&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        urlList.add("https://img1.baidu.com/it/u=843796730,1717565650&fm=253&fmt=auto&app=120&f=JPEG?w=400&h=400");

        for (int i = 0; i < strings.size(); i++) {


            View view =  LayoutInflater.from(this).inflate(R.layout.item_view_tag, null, false);
                TextView tvTag = view.findViewById(R.id.tv);
            tvTag.setText(strings.get(i));
            flowviewgroup.addView(view);

        }

        BGABanner banner = findViewById(R.id.banner);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Glide.with(FlowLayoutActivity.this)
                        .load(model)
//                        .placeholder(R.drawable.holder)
//                        .error(R.drawable.holder)
                        .centerCrop()
                        .dontAnimate()
                        .into((ImageView) itemView);
            }
        });
        banner.setData(urlList, strings);

        XBanner xBanner = findViewById(R.id.xbanner);
        //代码设置框架占位图，也可在布局中设置
        xBanner.setBannerPlaceholderImg(R.drawable.gg1, ImageView.ScaleType.FIT_CENTER);
        ArrayList<BannerBean> bannerBeans = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            BannerBean bannerBean = new BannerBean(urlList.get(i));
            bannerBeans.add(bannerBean);
        }


        //添加轮播图片数据（图片数据不局限于网络图片、本地资源文件、View 都可以）,刷新数据也是调用该方法
        xBanner.setBannerData(bannerBeans);//setData（）方法已过时，推荐使用setBannerData（）方法，具体参照demo使用
        //加载广告图片
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                Glide.with(FlowLayoutActivity.this).load(((BannerBean)
                        model).getXBannerUrl()).placeholder(R.drawable.gg1).error(R.drawable.gg1).into((ImageView) view);
            }
        });

    }
}
