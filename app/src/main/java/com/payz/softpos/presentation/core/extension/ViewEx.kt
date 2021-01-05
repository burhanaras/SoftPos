package com.payz.softpos.presentation.core.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible

inline fun View.setSingleClickListener(
    throttleTime: Long = 600L,
    crossinline action: (v: View) -> Unit
) {
    setOnClickListener(generateSingleClickListener(throttleTime, action))
}

inline fun generateSingleClickListener(
    throttleTime: Long = 600L,
    crossinline action: (v: View) -> Unit
): View.OnClickListener? {
    return object : View.OnClickListener {
        private var theFirstClickTime: Long = 0
        override fun onClick(view: View) {
            if (System.currentTimeMillis() - theFirstClickTime > throttleTime) {
                theFirstClickTime = System.currentTimeMillis()
                action(view)
            }

        }

    }
}

fun View.animateGone() {
    this.animate()
        .alpha(0f)
        .setDuration(1000)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isGone = true
            }
        })
}

fun View.animateVisible() {
    this.animate()
        .alpha(1f)
        .setDuration(1000)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isVisible = true
            }
        })
}