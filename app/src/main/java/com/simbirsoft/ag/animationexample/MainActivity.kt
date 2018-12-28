package com.simbirsoft.ag.animationexample

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressButton = ProgressButton(this)
        parent_panel.addView(progressButton)



        tv_button.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.sel_progress_rotate)
            val changeAnim = iv_animated.background as AnimationDrawable
            changeAnim.setEnterFadeDuration(2000)
            changeAnim.setExitFadeDuration(2000)
//            val anim = ValueAnimator.ofArgb(ContextCompat.getColor(this, android.R.color.holo_blue_light),
//                ContextCompat.getColor(this, android.R.color.holo_blue_dark))
//            anim.setEvaluator(ArgbEvaluator())
//
//            anim.addUpdateListener { valueAnimator ->
//                iv_animated.setBackgroundColor(valueAnimator.animatedValue as Int)
//
//            }
            if (iv_animated.isSelected && iv_animated.isActivated) {
                iv_animated.isSelected = false
                iv_animated.isActivated = false
                return@setOnClickListener
            }
            if (iv_animated.isSelected) {
                iv_animated.isActivated = true
                iv_animated.startAnimation(animation)
            } else if (!iv_animated.isSelected) {
                iv_animated.isSelected = true
                iv_animated.startAnimation(animation)
                changeAnim.start()
//                anim.start()
            }
        }

    }
}
