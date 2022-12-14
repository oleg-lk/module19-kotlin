package ru.oleshchuk.module19_kotlin.view

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.core.animation.doOnEnd
import ru.oleshchuk.module19_kotlin.R

class MyRatingView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet){
    private var _rating : Int = 90
    private var _arcOval = RectF()
    private var _xc : Float = 0F
    private var _yc : Float = 0F
    private var _rad : Float = 0F
    private var _widthArc = 20F
    private var _scaleAnimation = 1F
    private var _alphaAnimation = 255
    private val paintBack = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.FILL
    }
    private val paintRatingArc = Paint().apply {
        color = getRatingColor()
        style = Paint.Style.STROKE
        strokeWidth = _widthArc
        strokeCap = Paint.Cap.ROUND
        flags = Paint.ANTI_ALIAS_FLAG
    }
    private val paintRatingText = Paint().apply {
        color = getRatingColor()
        style = Paint.Style.FILL
        textSize = 50F
        typeface = Typeface.SANS_SERIF
        setShadowLayer(5F, 2F, 2F, Color.BLACK)
        flags = Paint.ANTI_ALIAS_FLAG
    }

    val alphaAnimator = ValueAnimator.ofPropertyValuesHolder().apply {
        duration = 1500
        interpolator = AccelerateInterpolator()
        addUpdateListener {
            _alphaAnimation = it.getAnimatedValue(KEY_ALPHA) as Int
            //Log.d("lkLog", "_scaleAnimation = $_scaleAnimation")
            invalidate()
        }
    }
    val scaleAnimator = ValueAnimator.ofFloat().apply {
        duration = 1000
        interpolator = BounceInterpolator()
        addUpdateListener {
            _scaleAnimation = (it.animatedValue as Float)
            //Log.d("lkLog", "_scaleAnimation = $_scaleAnimation")
            invalidate()
        }
        doOnEnd {
            //Log.d("lkLog", "end")
        }
    }

    fun startAnimation(){
        scaleAnimator.setFloatValues(0.1F,1F)
        val alphaPVH = PropertyValuesHolder.ofInt(KEY_ALPHA, 0, 255)
        alphaAnimator.setValues(alphaPVH)
        scaleAnimator.start()
        alphaAnimator.start()
    }

    companion object{
        const val KEY_ALPHA = "key alpha"
    }

    init {
        //changePaint()
        val attributes =
            context.theme.obtainStyledAttributes(attributeSet,
                R.styleable.MyRatingView, 0, 0)
        try {
            _widthArc = attributes.getFloat(R.styleable.MyRatingView_ratingStrokeWidth, 20F)
            _rating = attributes.getInteger(R.styleable.MyRatingView_ratingProgress, _rating)
        } finally {
            attributes.recycle()
        }
        /**/
        paintRatingArc.strokeWidth = _widthArc
    }

    private fun getRatingColor() : Int{
        return when(_rating){
            in 0..25 -> Color.argb(255, 230, 50, 50)
            in 26..50 -> Color.argb(255, 200, 110, 50)
            in 51..75 -> Color.argb(255, 200, 220, 50)
            else->Color.argb(255, 50, 220, 50)
        }
    }

    private fun getMySize(size: Int, measureSpec: Int) : Int{
        return when(MeasureSpec.getMode(measureSpec)){
            MeasureSpec.EXACTLY,MeasureSpec.UNSPECIFIED->size
            /*AT_MOST*/else-> size.coerceAtMost(MeasureSpec.getSize(measureSpec))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        //getDefaultSize()
        //resolveSize(size: Int, measureSpec: Int)
        val myWidth = getMySize(width, widthMeasureSpec)
        val myHeight = getMySize(height, heightMeasureSpec)

        setMeasuredDimension(myWidth, myHeight)
    }

    fun setRating(rate: Int){
        _rating = rate
        val radArc = (_rad-_widthArc-5F).coerceAtLeast(10F)
        _arcOval.set(_xc-radArc, _yc-radArc, _xc+radArc, _yc+radArc)
        /**/
        val col = getRatingColor()
        paintRatingArc.color = col
        paintRatingText.color = col
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val w2 = w.toFloat().div(2)
        val h2 = h.toFloat().div(2)
        _xc = w2
        _yc = h2
        _rad = w2.coerceAtMost(h2)
        setRating(_rating)
    }

    private fun drawRatingText(canvas: Canvas){
        val text = "%.1f".format(_rating * 0.1F)
        val measureText = paintRatingText.measureText(text)
        canvas.drawText(text, _xc-measureText/2F, _yc+paintRatingText.fontMetrics.descent, paintRatingText)
    }

    private fun drawRatingArc(canvas: Canvas){
        /**/
        paintRatingArc.alpha = _alphaAnimation
        canvas.drawArc(_arcOval, -90F, _rating*3.6F, false, paintRatingArc)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            /*background*/
            drawCircle(_xc, _yc, _rad*_scaleAnimation, paintBack)
            /**/
            drawRatingArc(canvas)
            /**/
            drawRatingText(canvas)
        }
    }
}