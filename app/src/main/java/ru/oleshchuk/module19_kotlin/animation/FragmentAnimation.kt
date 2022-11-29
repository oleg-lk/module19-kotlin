package ru.oleshchuk.module19_kotlin.animation

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import ru.oleshchuk.module19_kotlin.MainActivity
import java.util.concurrent.Executors
import kotlin.math.hypot

object FragmentAnimation {
    fun animateFragment(view: View, activity: Activity, pos : Int){

        Executors.newSingleThreadScheduledExecutor().execute {

            //В бесконечном цикле проверяем, когда наше анимированное view будет "прикреплено" к экрану
            while(!view.isAttachedToWindow){
            }

            activity.runOnUiThread {
                val sizeMenuItems = (activity as MainActivity).getSizeNavMenu()
                val dx = view.width/(sizeMenuItems*2)
                val centerX = (view.width/sizeMenuItems) * (pos-1) + dx
                val centerY = view.bottom
                val radius = hypot(view.width.toDouble(), view.height.toDouble())
                ViewAnimationUtils.createCircularReveal(view,
                    centerX,
                    centerY,
                    0F, radius.toFloat()).apply {
                    duration = 800
                    interpolator = FastOutSlowInInterpolator()
                    start()
                }

                view.visibility = View.VISIBLE
            }
        }
    }
}