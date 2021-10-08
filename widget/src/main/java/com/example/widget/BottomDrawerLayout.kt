package com.example.widget

import android.animation.Animator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
//import kotlinx.android.synthetic.main.camera_drawerlayout_white.view.*
import me.jessyan.autosize.utils.AutoSizeUtils
import java.lang.Math.abs

/**
 * 底部抽屉布局
 */
class BottomDrawerLayout: FrameLayout  {


    companion object{
        private const val TAG:String = "BottomDrawerLayout"

    }

    constructor(context: Context,attrs:AttributeSet?):this(context,attrs,0)
    constructor(context: Context,attrs:AttributeSet?,defStyle:Int):super(context,attrs,defStyle){
    }



    var hasDown = false
    var whiteShowing = false
    var whiteHiding = false
    var lp: LinearLayout.LayoutParams? = null
    var maxHeight = 250F
    var minHeight = 50F

    var moveY = 0f
    var startY = 0f
    var moveX = 0f
    var startX = 0f
    var maxHeightPx = AutoSizeUtils.dp2px(context, maxHeight)

    var minHeightPx = AutoSizeUtils.dp2px(context, minHeight)
    /**配置动画时间*/
    private var animDurations = 200L
    init {
        View.inflate(context,R.layout.camera_drawerlayout_white, this)
        var layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.height = maxHeightPx
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        this.layoutParams = layoutParams
//        var dp2px = AutoSizeUtils.dp2px(context, maxHeight)
        Log.d(TAG,"onActionSlip height===init height maxHeight${this.layoutParams.height}====this.height ==${this.height})")
        findViewById<LinearLayout>(R.id.llDrawerWhite).setOnTouchListener { _, event ->
            onActionSlip(event, 4)
        }

        findViewById<TextView>(R.id.tvTest).setOnTouchListener { _, event ->
            onActionSlip(event, 6)
        }

    }

    private fun onActionSlip(
        event: MotionEvent, actionType: Int
    ): Boolean {
        moveY = event.y
        moveX = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG,"onActionSlip ACTION_DOWN===startY$moveY")
                if (!hasDown) {
                    startY = moveY
                    startX = moveX
                    hasDown = true
                }
            }
            MotionEvent.ACTION_MOVE ->{

                Log.d(TAG,"onActionSlip ACTION_MOVE===moveY$moveY == height = ${this.height}==move distance==${moveY-startY}===this.Layoutparms.height ==${this.layoutParams.height}")
                var layoutParams: RelativeLayout.LayoutParams = this.layoutParams as RelativeLayout.LayoutParams
                var newHeight = (this.height - (moveY-startY)).toInt()
                Log.d(TAG,"onActionSlip ACTION_MOVE===measuredheight = ${this.measuredHeight}===  this.height==${this.height} ===newHeight$newHeight ===+maxHeight==$maxHeightPx")
                if(newHeight> maxHeightPx){
                    newHeight = this.measuredHeight
//                    newHeight = maxHeightPx
                }
                if (newHeight<minHeightPx){
                    newHeight = minHeightPx
                }

//                Log.d(TAG,"onActionSlip ACTION_MOVE===newHeight$newHeight")
                layoutParams.height = newHeight
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                this.layoutParams = layoutParams

            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onActionSlip ACTION_MOVE_UP==startY:$startY -----moveT: $moveY")

                hasDown = false
                if (actionType==6){
                    Toast.makeText(context,"点击按钮测试",Toast.LENGTH_SHORT).show()
                }
                if (actionType==5) {
                    if((moveY - startY)>20){
                        showWhite()

                    }
                }else if (actionType==4) {
                    if (this.measuredHeight > AutoSizeUtils.dp2px(context, maxHeight) / 2) {
                        scrollAnimRunWhite(
                            this.measuredHeight,
                            AutoSizeUtils.dp2px(context, maxHeight)
                        )
                    } else {
                        scrollAnimRunWhite(this.measuredHeight, AutoSizeUtils.dp2px(context,minHeight))
                    }
                }
                if (abs(startY - moveY) < 6 && abs(startX - moveX) < 6) {
                    //0 整个布局 1标签 2诊疗状态和日期 3顶部按钮
                    //响应点击事件
                    if (actionType == 1) {
                        //点击标签
//                        goEditTags()
                    } else if (actionType == 2) {
                        //点击诊疗日期和zhuangt
//                        goEditDataAndState()
                    } else if (actionType == 3) {
                        //点击顶部箭头开管

                    }
                } else {
                    //响应滑动事件

                    //如果滑动的是标签切是水平滑动 不处理
                    if (actionType == 1 && abs(startX - moveX) > 10 && abs(startY - moveY) < 10) {
                        return false
                    }

                }

            }
            else -> {
            }
        }

        return true
    }

    fun showWhite() {
        if (!whiteShowing) {
            whiteShowing = true

            scrollAnimRunWhite(
                this.measuredHeight,
                AutoSizeUtils.dp2px(context, maxHeight)
            )
        }


    }
    fun hideWhite(){
        if (!whiteHiding) {
            whiteHiding = true
            scrollAnimRunWhite(
                this.measuredHeight,
                0
            )
        }

    }
    private fun scrollAnimRunWhite(startV: Int, endV: Int) {
//        LogUtil.d("AAAAAAAAAAAA","scrollAnimRunWhite   startV== $startV ===endV ${endV}")

        val anim = ValueAnimator.ofObject(HeightEvaluater(), startV, endV)
        anim.duration = animDurations
        anim.addUpdateListener {
            val animatedValue = it.animatedValue as Int
            var layoutParams: RelativeLayout.LayoutParams = this.layoutParams as RelativeLayout.LayoutParams

            layoutParams.height = animatedValue
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            this.layoutParams = layoutParams

        }
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {

                if (endV==0) {
                    visibility = View.GONE

                    whiteHiding = false
                }else{
                    whiteShowing = false
                    visibility = View.VISIBLE
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
//                LogUtil.d("TAG", "onAnimationCancel====onAnimationCancel  ")
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        anim.start()


    }
    inner class HeightEvaluater : TypeEvaluator<Int> {
        override fun evaluate(fraction: Float, startValue: Int?, endValue: Int?): Int {
            return (startValue!! + fraction * (endValue!! - startValue)).toInt()
        }

    }
}