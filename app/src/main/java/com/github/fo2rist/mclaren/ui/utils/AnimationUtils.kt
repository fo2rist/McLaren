@file:JvmName("AnimationUtils")
package com.github.fo2rist.mclaren.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator

const val EXTRA_CIRCULAR_REVEAL_X = "circular_reveal_x"
const val EXTRA_CIRCULAR_REVEAL_Y = "circular_reveal_y"

private const val REVEAL_DURATION_MS = 400L
private const val REVEAL_RADIUS_OFFSET = 1.1f // multiplier for max width/height -> circular radius conversion

/**
 * Start new activity for transition and put reveal parameters to intent.
 */
fun startActivityWithRevealAnimation(activity: Activity, intent: Intent, revealCenterView: View) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity, revealCenterView, "floatig_button_transmission")
    putRevealAnimationCenterToIntent(intent, revealCenterView)

    ActivityCompat.startActivity(activity, intent, options.toBundle())
}

private fun putRevealAnimationCenterToIntent(intent: Intent, revealCenterView: View) {
    val revealX = (revealCenterView.x + revealCenterView.width / 2).toInt()
    val revealY = (revealCenterView.y + revealCenterView.height / 2).toInt()

    intent.putExtra(EXTRA_CIRCULAR_REVEAL_X, revealX)
    intent.putExtra(EXTRA_CIRCULAR_REVEAL_Y, revealY)
}

/**
 * Animate reveal of given view from coordinates specified in intent.
 */
fun animateReveal(view: View, intent: Intent) {
    view.visibility = View.INVISIBLE

    val (centerX, centerY) = getRevealAnimationCenterFromIntent(intent)

    val viewTreeObserver = view.viewTreeObserver
    if (viewTreeObserver.isAlive) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startRevealAnimation(view, centerX, centerY)
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}

private fun getRevealAnimationCenterFromIntent(intent: Intent): Pair<Int, Int> {
    val revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
    val revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)
    return Pair(revealX, revealY)
}


private fun startRevealAnimation(view: View, centerX: Int, centerY: Int) {
    // make the view visible and start the animation
    view.visibility = View.VISIBLE

    val finalRadius = Math.max(view.width, view.height) * REVEAL_RADIUS_OFFSET
    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0f, finalRadius).apply {
        duration = REVEAL_DURATION_MS
        interpolator = AccelerateInterpolator()
    }.start()
}

/**
 * Animate un-reveal of given view.
 * @param doAfter action to execute once animation is over.
 */
fun animateUnreveal(view: View, doAfter: () -> Unit) {
    val startRadius = Math.max(view.width, view.height) * REVEAL_RADIUS_OFFSET
    val centerX = view.width
    val centerY = view.height
    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, 0f).apply {
        duration = REVEAL_DURATION_MS
    }.addOnEndListener {
        view.visibility = View.INVISIBLE
        doAfter()
    }.start()
}

private fun Animator.addOnEndListener(onEnd: () -> Unit) : Animator{
    this.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            onEnd()
        }
    })
    return this
}
