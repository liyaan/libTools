package com.liyaan.myffmepgfirst.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import com.liyaan.myffmepgfirst.R
import java.util.*

class GraphicsVerifyView:View {
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    private val pathSeekBg:Path = Path()
    private var circleRadius = 0f
    private var seekTop = 0f;
    private var isTouch = false
    private var currentStatus = Status.DEFAULT
    private var seekMoveX = 0f
    private var seekCenterX = 0f
    private var defaultDegrees = 0f
    private var currentDegrees = 0f
    private var imgSrc = R.drawable.ic_img
    var seekBorderWidth = 4f
    var offsetDegrees = 10f
    var verifyCallBack: VerifyCallBack? = null
    var seekBgColor = Color.parseColor("#EEEEEE")
    var seekDefaultColor = Color.WHITE
    var seekBorderColor = Color.GRAY
    var seekTouchColor = Color.BLUE
    var seekVerFailColor = Color.RED
    var seekVerSuccessColor = Color.GREEN
    var seekArrowDefaultColor = Color.GRAY
    var seekArrowTouchColor = Color.WHITE
    constructor(context:Context):this(context,null)
    constructor(context: Context?, attrs: AttributeSet?):this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?,
                defStyleAttr: Int):super(context, attrs, defStyleAttr){
        initAttr(attrs)
        init()
    }
    private fun initAttr(attrs: AttributeSet?) {
        attrs?.let {
            val typeStyle = context.obtainStyledAttributes(it,R.styleable.GraphicsVerifyView)
            seekBorderWidth = typeStyle.getDimension(R.styleable.GraphicsVerifyView_seekBorderWidth,4f)
            offsetDegrees = typeStyle.getFloat(R.styleable.GraphicsVerifyView_offsetDegrees,10f) //允许的误差角度
            seekBgColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekBgColor,Color.parseColor("#EEEEEE")) //滑块背景颜色
            seekDefaultColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekDefaultColor,Color.WHITE) //滑块默认颜色
            seekBorderColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekBorderColor,Color.GRAY) //滑块描边颜色
            seekTouchColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekTouchColor,Color.BLUE) //滑块触摸的颜色
            seekVerFailColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekVerFailColor,Color.RED) //滑块验证失败的颜色
            seekVerSuccessColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekVerSuccessColor,Color.GREEN) //滑块验证成功的颜色
            seekArrowDefaultColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekArrowDefaultColor,Color.GRAY) //滑块箭头默认颜色
            seekArrowTouchColor = typeStyle.getColor(R.styleable.GraphicsVerifyView_seekArrowTouchColor,Color.WHITE) //滑块箭头触摸颜色
            imgSrc = typeStyle.getResourceId(R.styleable.GraphicsVerifyView_imgSrc,R.drawable.ic_img)
            typeStyle.recycle()
        }
    }
    private fun init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null)
        bitmap = BitmapFactory.decodeResource(context.resources,imgSrc)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val randomValue = Random().nextInt(201) + 80f
        defaultDegrees = -randomValue
    }
    fun setImageSrc(@DrawableRes imgSrc:Int){
        this.imgSrc = imgSrc
        bitmap = if(width>0){
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources,imgSrc),width,width,true)
        }else{
            BitmapFactory.decodeResource(context.resources,imgSrc)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measuredWidth(widthMeasureSpec)
        setMeasuredDimension(width,measureHeight(width))
    }

    private fun measureHeight(width: Int): Int {
        return (width / 10f + width / 2f + width / 6f + seekBorderWidth / 2f).toInt()
    }

    private fun measuredWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if(MeasureSpec.AT_MOST == mode){
            width = 300
        }
        return width
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val scaleValue = w/2
        bitmap = Bitmap.createScaledBitmap(bitmap,scaleValue,scaleValue,true)
        seekTop = width/10f+bitmap.width
        circleRadius = width/12f
        initSeekBgPath()
    }

    private fun initSeekBgPath() {
        val top = seekTop
        val borderOffset = seekBorderWidth/2
        pathSeekBg.moveTo(circleRadius+borderOffset,top)
        pathSeekBg.addArc(borderOffset,top ,circleRadius * 2 + borderOffset,
            top + circleRadius * 2,-90f,-180f)
        pathSeekBg.lineTo(width - circleRadius - borderOffset,
            top + circleRadius * 2)
        pathSeekBg.addArc(width - circleRadius * 2 - borderOffset,top ,
            width - borderOffset,top + circleRadius * 2,90f,-180f)
        pathSeekBg.lineTo(circleRadius + borderOffset,top)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawSeekBg(it)
            drawSeek(it)
            drawCircleImage(it)
        }
    }

    private fun drawCircleImage(canvas: Canvas) {
        currentDegrees = (seekCenterX - circleRadius) / (width - circleRadius * 2) * 360 + defaultDegrees
        canvas.save()
        canvas.translate(width/2f,width/4f)
        canvas.rotate(currentDegrees)
        canvas.drawCircle(0f ,0f,(width / 4).toFloat(),paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(bitmap,-bitmap.width / 2f,-bitmap.width / 2f,paint)
        paint.xfermode = null
        canvas.restore()
    }

    private fun drawSeekBg(canvas: Canvas){
        paint.color = seekBgColor
        paint.strokeWidth = seekBorderWidth
        paint.style = Paint.Style.FILL
        canvas.drawPath(pathSeekBg,paint)
        paint.color = seekBorderColor
        paint.style = Paint.Style.STROKE
        canvas.drawPath(pathSeekBg,paint)
    }
    private fun drawSeek(canvas: Canvas) {
        seekCenterX = when{
            seekMoveX<circleRadius->{
                circleRadius
            }
            seekMoveX>width-circleRadius->{
                width - circleRadius
            }
            else->{
                seekMoveX
            }
        }
        val centerY = seekTop+circleRadius
        paint.color = when{
            isTouch -> {
                seekTouchColor
            }
            currentStatus == Status.SUCCESS -> {
                seekVerSuccessColor
            }
            currentStatus == Status.FAIL -> {
                seekVerFailColor
            }
            else -> {
                seekDefaultColor
            }
        }
        paint.style = Paint.Style.FILL
        canvas.drawCircle(seekCenterX ,centerY,circleRadius - seekBorderWidth,paint)
        paint.style = Paint.Style.STROKE
        paint.color = when{
            isTouch -> {
                seekTouchColor
            }
            currentStatus == Status.SUCCESS -> {
                seekVerSuccessColor
            }
            currentStatus == Status.FAIL -> {
                seekVerFailColor
            }
            else -> {
                seekBorderColor
            }
        }
        paint.strokeWidth = seekBorderWidth
        canvas.drawCircle(seekCenterX,centerY,circleRadius - seekBorderWidth,paint)
        paint.textSize = circleRadius
        paint.color = when{
            isTouch -> {
                seekArrowTouchColor
            }
            currentStatus == Status.SUCCESS -> {
                seekArrowTouchColor
            }
            currentStatus == Status.FAIL -> {
                seekArrowTouchColor
            }
            else -> {
                seekArrowDefaultColor
            }
        }
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = 2f
        val fontMetrics = paint.fontMetrics
        val fontHeight = fontMetrics.bottom - fontMetrics.top
        val textBaseY = (circleRadius * 2 - fontHeight) / 2
        canvas.save()
        canvas.translate(seekCenterX,centerY)
        canvas.rotate(180f)
        canvas.drawText("ㄍ",0f,textBaseY,paint)
        canvas.restore()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    //当状态为默认的时候才可以拖动
                    if(currentStatus == Status.DEFAULT){
                        //判断触摸点是否在滑块上
                        val rectF = RectF(0f,seekTop,circleRadius * 2,seekTop + circleRadius * 2)
                        if(rectF.contains(event.x,event.y)){
                            isTouch = true
                            postInvalidate()
                        }
                    }
                }
                MotionEvent.ACTION_MOVE->{
                    if(isTouch){
                        seekMoveX = event.x
                        postInvalidate()
                    }
                }
                MotionEvent.ACTION_UP->{
                    currentStatus = if(currentDegrees <= offsetDegrees && currentDegrees >= -offsetDegrees){
                        //验证成功
                        Status.SUCCESS
                    }else{
                        //验证失败
                        Status.FAIL
                    }
                    verifyCallBack?.let {
                        if(currentStatus == Status.SUCCESS){
                            it.onSuccess()
                        }else{
                            it.onFail()
                        }
                    }
                    isTouch = false
                    postInvalidate()
                }
                else -> {}
            }
        }
        return isTouch
    }

    fun reset(){
        seekMoveX = 0f
        currentStatus = Status.DEFAULT
        val randomValue = Random().nextInt(201) + 80f
        defaultDegrees = -randomValue
        postInvalidate()
    }
    /**
     * 验证的三个状态
     */
    enum class Status{
        DEFAULT,//默认
        FAIL, //失败
        SUCCESS //成功
    }

    /**
     * 验证的回调
     */
    interface VerifyCallBack{
        fun onSuccess()
        fun onFail()
    }
}