package com.example.homework_demo

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.CursorAnchorInfo

class AnimView: View {
    //创建动画集合保存动画
    var animators = mutableListOf<Animator>()
    //小球的半径
    var ballRadius = 0f
    //定义中心小球的圆心
    var cx = 0f
    var cy = 0f
    //矩形移动因子
    var rectMoveLength = 0f
    //颜色改变使得移动因子
    var colorMove = 0f
    //创建画笔
    var mpaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.MAGENTA
    }
    var mpaint2 = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    //复写构造方法
    constructor(context:Context):super(context)
    constructor(contest: Context,attr:AttributeSet): super(contest,attr)

    //获取位置及半径等参数
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //获获圆心
        cx = width/2f
        cy = height/2f
        //获取半径
        if(width <= height){
            ballRadius = width/6f
        }else{
            ballRadius = height/2f
            if (width < 6*ballRadius){
                ballRadius = width/6f
            }
        }
        Log.v("zj","ballRadius:${ballRadius}")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f+colorMove,0f,6*ballRadius,height.toFloat(),mpaint)
        canvas?.drawRect(0f+rectMoveLength,0f,colorMove-rectMoveLength,height.toFloat(),mpaint2)
    }
    //外部接口
    fun loadAnim(){
        //颜色改变动画
        ValueAnimator.ofFloat(0f,6*178.5f).apply {
            duration = 500
            addUpdateListener {
                colorMove = animatedValue as Float
                invalidate()
            }
            animators.add(this)
        }
        //矩形动画
        ValueAnimator.ofFloat(0f, 3*178.5f).apply {
            duration = 800
            addUpdateListener {
                rectMoveLength = animatedValue as Float
                invalidate()
            }
            animators.add(this)
        }

    }


    fun show(){
        loadAnim()
//        for (item in animators){
//            item.start()
//        }
        AnimatorSet().apply {
              play(animators[1]).after(animators[0])
            start()
        }

    }
}