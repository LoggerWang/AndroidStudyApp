package com.legend.androidstudyapp.customerview

import android.animation.Animator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.legend.androidstudyapp.R
import kotlinx.android.synthetic.main.camera_drawerlayout_white.*
import me.jessyan.autosize.utils.AutoSizeUtils
import java.util.ArrayList
import kotlin.math.abs

/**
 * @author wanglezhi
 * @date   2021/5/18 11:39
 * @discription
 */
class CustomerViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customerview)
        llDrawerWhite.setOnTouchListener { _, event ->
            onActionSlip(event, 4)
        }
    }

    var hasDown = false
    var whiteShowing = false
    var whiteHiding = false
    var lp: LinearLayout.LayoutParams? = null
    var maxHeight = 0
    var minHeight = 0

    var moveY = 0f
    var startY = 0f
    var moveX = 0f
    var startX = 0f

    /**配置动画时间*/
    private var animDurations = 200L
    private fun onActionSlip(
            event: MotionEvent, actionType: Int
    ): Boolean {
        moveY = event.y
        moveX = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                LogUtil.d(TAG,"onActionSlip ACTION_DOWN")
                if (!hasDown) {
                    startY = moveY
                    startX = moveX
                    hasDown = true
                }
            }
            MotionEvent.ACTION_MOVE ->{
                moveY-startY
                var layoutParams: RelativeLayout.LayoutParams = llDrawerWhite.layoutParams as RelativeLayout.LayoutParams
                var newHeight = (llDrawerWhite.height - (moveY-startY)).toInt()
                if(newHeight> AutoSizeUtils.dp2px(this,235F)){
                    newHeight = llDrawerWhite.measuredHeight
                }
                layoutParams.height = newHeight
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                llDrawerWhite.layoutParams = layoutParams

            }
            MotionEvent.ACTION_UP -> {
//                LogUtil.d("ACTION_MOVE_UP", "startY:$startY -----moveT: $moveY")

                hasDown = false
                if (actionType==5) {
                    if((moveY - startY)>20){
//                            showMediaAdapter?.getImageViewHolder().
                        showWhite()

                    }
                }else if (actionType==4) {
                    if (llDrawerWhite.measuredHeight > AutoSizeUtils.dp2px(this, 235F) / 2) {
                        scrollAnimRunWhite(
                                llDrawerWhite.measuredHeight,
                                AutoSizeUtils.dp2px(this, 235F)
                        )
                    } else {
                        scrollAnimRunWhite(llDrawerWhite.measuredHeight, 0)
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
                    llDrawerWhite.measuredHeight,
                    AutoSizeUtils.dp2px(this, 235F)
            )
        }


    }
    fun hideWhite(){
        if (!whiteHiding) {
            whiteHiding = true
            scrollAnimRunWhite(
                    llDrawerWhite.measuredHeight,
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
            var layoutParams:RelativeLayout.LayoutParams = llDrawerWhite.layoutParams as RelativeLayout.LayoutParams

            layoutParams.height = animatedValue
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            llDrawerWhite.layoutParams = layoutParams

        }
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
//                LogUtil.d("AAAAAAAAAAAA","onAnimationEnd   ")

                if (endV==0) {
                    llDrawerWhite.visibility = View.GONE
//                    whiteShow = false
                    whiteHiding = false
                }else{
                    whiteShowing = false
//                    whiteShow = true
                    llDrawerWhite.visibility = View.VISIBLE
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