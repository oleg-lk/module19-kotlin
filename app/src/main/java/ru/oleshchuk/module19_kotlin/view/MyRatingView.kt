package ru.oleshchuk.module19_kotlin.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyRatingView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet){

    private var _xc : Float = 0F
    private var _yc : Float = 0F
    private var _rad : Float = 0F
    private lateinit var paintBack : Paint

    init {
        paintBack = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.FILL
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        MeasureSpec.getSize()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val w2 = w.toFloat().div(2)
        val h2 = h.toFloat().div(2)
        _xc = w2
        _yc = h2
        _rad = w2.coerceAtMost(h2)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawCircle(_xc, _yc, _rad, paintBack)
        }
    }
}