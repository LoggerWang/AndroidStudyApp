package com.legend.androidstudyapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TempRecyclerView extends RecyclerView {
    public TempRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public TempRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }
    LinearLayoutManager linearLayoutManager;

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        linearLayoutManager = (LinearLayoutManager) layout;
    }


    int firstc, lastc, first, last, distance =0,flagend=0, flagfirst =0,offsetX = 0,offsetY = 0,offseta,left,top,offset,postion,viewWidth = 0,viewHeight = 0;
    View lastview,firstview,one,two,view,lastv, three;
    boolean flag = true,fsfsf = false;

    @Override
    public void onScrollStateChanged(int state) {

        super.onScrollStateChanged(state);

        if(state == 0){
            postion = linearLayoutManager.findFirstVisibleItemPosition();
            view = linearLayoutManager.findViewByPosition(postion);
            top = view.getTop();
//            left = view.getLeft();
            if(viewWidth == 0){
                viewWidth = view.getWidth();
            }


            float marginLeftFirst = 25;
            float marginLeftSecond = 35;
            float marginLeft = 15;
            //可以自动滑动 第一个完全可见
//            int firstPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition(); //第一个完整可见item
//            firstview = linearLayoutManager.findViewByPosition(firstPos);
            int marleftFirst = DensityUtil.dip2px(getContext(), marginLeftFirst);
            int marleftSecond = DensityUtil.dip2px(getContext(), marginLeftSecond);
            int marLeft = DensityUtil.dip2px(getContext(), marginLeft);
            int offX =firstview.getLeft()-marleftFirst;
//            System.out.println("===onScrollStateChanged===left==="+left+"==viewWidth"+viewWidth);

//            System.out.println("===onScrollStateChanged======"+left+"==left==75dp==px=="+DensityUtil.dip2px(getContext(),25F));
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
            int leftMargin = layoutParams.leftMargin;
//            System.out.println("===onScrollStateChanged===leftMargin==="+leftMargin+"==DensityUtil.dip2px(getContext(),10F)="+DensityUtil.dip2px(getContext(),25F));

            if (left == marleftFirst){
                System.out.println("===onScrollStateChanged===left == leftMargin+DensityUtil.dip2px(getContext(),10F)==="+left+"==viewWidth"+viewWidth);
                return;
            }
            else if (Math.abs(marleftFirst-left)<viewWidth/2) {
                System.out.println("===onScrollStateChanged===75-left==="+(marleftFirst-left));
                smoothScrollBy(-(marleftFirst-left),0);
            }else {
                System.out.println("===onScrollStateChanged===firstview.getLeft()==="+firstview.getLeft());
                smoothScrollBy((int) -(one.getLeft()-marLeft), 0);
            }

        }
    }



    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        offsetY+=dy;
        offsetX+=dx;
        firstc = linearLayoutManager.findFirstCompletelyVisibleItemPosition(); //第一个完整可见item
        lastc = linearLayoutManager.findLastCompletelyVisibleItemPosition();//最后一个完整可见item
        first = linearLayoutManager.findFirstVisibleItemPosition();//第一个可见item
        last = linearLayoutManager.findLastVisibleItemPosition();//最后一个可见item
        firstview = linearLayoutManager.findViewByPosition(firstc);
        one = linearLayoutManager.findViewByPosition(first);
        two = linearLayoutManager.findViewByPosition(first +1);//第二可见item  --- 用来防止滑动过快item不规格变化
        three = linearLayoutManager.findViewByPosition(first + 2);//第三可见item --- 用来防止滑动过快item不规格变化
        lastv = linearLayoutManager.findViewByPosition(last);
//        if (viewHeight == 0){
//            flagfirst = first;
//            flagend = lastc;
//            viewHeight = firstview.getHeight();
//            lastview = linearLayoutManager.findViewByPosition(lastc);
//        }
        if (viewWidth == 0){
            flagfirst = first;
            flagend = lastc;
            viewWidth = firstview.getWidth();
            lastview = linearLayoutManager.findViewByPosition(lastc);
        }
        if (flagfirst == first -1) {
            flag = true;
            lastview = linearLayoutManager.findViewByPosition(lastc);
        }else if (flagfirst == first +1) {
            flag = false;
            lastview = linearLayoutManager.findViewByPosition(lastc);
        }
//        if (firstview!=null) {
//            offseta = firstview.getTop();
//        }
        if (firstview!=null) {
            offseta = firstview.getLeft();
        }
        System.out.println("====offseta========offseta="+offseta+"===viewWith=="+viewWidth+"==offseta/viewWidth/2=="+offseta/viewWidth/2);
//        float sx = 1f+(float) offseta/viewHeight/2;
        float sx = 1f+(float) offseta/(viewWidth+50)/5;
        System.out.println("===sx=sx===sx=="+sx);
//        if(offsetY == 0){
//            one.setScaleX(1.5f);
//        }else
//            one.setScaleX(sx);

        if(offsetX == 0){
            System.out.println("===if=offsetX===0");
            one.setScaleX(1.2f);
            one.setScaleY(1.2f);
        }else{
            System.out.println("===else=offsetX========"+sx);
            one.setScaleX(sx);
            one.setScaleY(sx);
        }

        //因为最后一个不是可见item不是完全可见，所以需要减去最后一个完全可见与height的距离
//        if (distance ==0)
//            distance =getHeight()-lastview.getBottom();
//        if (lastview!=null) {
//            offseta = getHeight()-lastview.getBottom()- distance;
//        }
        if (distance ==0)
            distance =getWidth()-lastview.getRight();
        if (lastview!=null) {
            offseta = getWidth()-lastview.getRight()- distance;
        }

//        if (dy!=0) {
//            if (!flag)
//                offseta = getHeight() - lastview.getBottom() + (viewHeight - distance) + 20;//20对应ItemDecoration的bottom
//        }
        if (dx!=0) {
            if (!flag)
                offseta = getWidth() - lastview.getRight() + (viewWidth - distance) + 40;//20对应ItemDecoration的bottom
        }
        if (offseta<0)
            offseta = 0;
//        sx = 1f+(float) offseta/viewHeight/2;
        sx = 1f+(float) offseta/(viewWidth+50)/5;
        two.setScaleX(sx);
        two.setScaleY(sx);
        lastv.setScaleX(1f);
        lastv.setScaleY(1f);
        if (three!=null) {
            three.setScaleX(1f);
            three.setScaleY(1f);
        }
        flagfirst = first;
        if (firstc == first) {//发生偏移时强制变换
            one.setScaleX(1.2f);
            one.setScaleY(1.2f);
            two.setScaleX(1f);
            two.setScaleY(1f);
        }
        left = one.getLeft();
    }

    //抛掷速度
    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY = 0;//不可抛掷
        return super.fling(velocityX, velocityY);
    }

}

