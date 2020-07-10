package com.lancer.eyelast.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author lancer
 * @des
 * @Date 2020/7/9 13:08
 */
class PathView : View {
    init {

        initPaint()
    }

    lateinit var paint: Paint
    val path: Path = Path()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private fun initPaint() {
        paint = Paint()
        //在Paint中设置了Shader后就不在使用setColor设置颜色了,但是没有直接的Shader类，而是
        // LinearGradient RadialGradient SweepGradient BitmapShader ComposeShader
        val shader: Shader = LinearGradient(
            100f, 100f, 500f, 500f,
            Color.parseColor("#E91E63"),
            Color.parseColor("#2196F3"), Shader.TileMode.CLAMP
        )
        paint.shader = shader

        //类似setColor和setShader都是设置颜色的
        paint.colorFilter = LightingColorFilter(0x00ffff, 0x000000)

        //setPathEffect，设置图形的轮廓效果，对所有图形有效,
        // CornerPathEffect(所有拐角都变成圆角)。DiscretePathEffect(让轮廓变成乱七八糟)，DashPathEffect(虚线)
        val pathEffect: PathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10f)
        paint.pathEffect = pathEffect
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置填充方式,其中WINDING 就是全填充(默认)，EVEN_ODD 表示的是交叉填充，带INVERSE的就是反色版本
        path.fillType = Path.FillType.EVEN_ODD
        //lineTo代表的是由当前位置(0,0)向(100,100)画一条直线，绝对坐标
        path.lineTo(100f, 100f)
        //rLineTo代表的是由当前位置(100,100)向正右方向100像素画一条直线,相对坐标
        path.rLineTo(100f, 0f)


    }
}