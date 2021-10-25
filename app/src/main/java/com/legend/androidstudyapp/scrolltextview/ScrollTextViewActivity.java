package com.legend.androidstudyapp.scrolltextview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.legend.androidstudyapp.R;
import com.legend.androidstudyapp.scrolltextview.entity.Item;
import com.legend.androidstudyapp.scrolltextview.view.SmoothScrollLayout;
import com.legend.androidstudyapp.scrolltextview.view.TextSwitcherView;
import com.legend.androidstudyapp.scrolltextview.view.VerticalScrollLayout;
import com.legend.androidstudyapp.scrolltextview.view.VerticalScrollTextView;
import com.ly.autoscrolllayout.entity.SwitcherItem;

import java.util.ArrayList;

/**
 * date：2021/10/25 16:21
 *
 * @author wanglezhi
 * desc:
 */
public class ScrollTextViewActivity extends AppCompatActivity {

    private VerticalScrollTextView v_text_view;
    private VerticalScrollLayout scroll_layout;
    private TextSwitcherView textSwitcherView;
    private SmoothScrollLayout smoothScrollLayout;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolltextview);
        v_text_view = findViewById(R.id.v_text_view);
        scroll_layout = findViewById(R.id.scroll_layout);
        textSwitcherView = findViewById(R.id.textSwitcherView);
        smoothScrollLayout = findViewById(R.id.smoothScrollLayout);
        initSmoothScrollLayout();
        initVScrollLayout();
        initVTextView();
        initTextSwitcherView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (scroll_layout!=null) {
            scroll_layout.startFlipping();
        }
        if (v_text_view!=null) {
            v_text_view.startPlay();
        }
        if (textSwitcherView!=null) {
            textSwitcherView.startPlay();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scroll_layout!=null) {
            scroll_layout.stopFlipping();
        }
        if (v_text_view!=null) {
            v_text_view.stopPlay();
        }
        if (textSwitcherView!=null) {
            textSwitcherView.stopPlay();
        }
    }


    private void initTextSwitcherView() {
        textSwitcherView.setSwitcherLayout(R.layout.item_switcher_view);
        ArrayList<SwitcherItem>  list= new ArrayList<>();
        list.add(new SwitcherItem("热门", "冬季韩版女子大衣，折扣价15.6元起"));
        list.add(new SwitcherItem("新品", "兔耳朵卡通亲子保暖防滑棉拖鞋"));
        list.add(new SwitcherItem("如何", "黄色卫衣+牛仔裤，开春少女必备穿搭"));
        textSwitcherView.setData(list);
    }


    /**
     * 仿中奖滚动
     */
    private void initSmoothScrollLayout() {

        ArrayList<String>  list = new ArrayList<>();
        list.add("张三购买彩票中了20W");
        list.add("187****0405购买彩票中了20W");
        list.add("李四购买彩票中了超级大礼包一个，获得飞机票两张");
        list.add("156***9876购买彩票中了三等奖");
        list.add("134***4235购买彩票中了特等奖，并获得海南三亚双人双飞游套餐一个");
        smoothScrollLayout.setData(list);
    }


    private void initVScrollLayout() {
        TestAdapter adapter = new TestAdapter();
        scroll_layout.setAdapter(adapter);

        ArrayList<Item>  items= new ArrayList<>();
        for (int i=0;i<5;i++) {
            Item item = new Item();
            item.title = "标签$i";
            item.text = "应该显示的内容标题$i";
            items.add(item);
        }
        adapter.setList(items);
    }

    private void initVTextView() {
        ArrayList<String>  data= new ArrayList<>();
        for (int i=0;i<4;i++) {
            data.add(String.format("测试竖向滚动文字%s", i));
        }
        v_text_view.setDataSource(data);
    }
}
