package com.lancer.eyelast.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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