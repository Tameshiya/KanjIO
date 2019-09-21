package jp.rei.andou.kanjio

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.PathParser


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            KanjiView(
                this,
                listOf(
                    PathParser.createPathFromPathData("M33.75,19.87c0.75,1.05,1.25,2.3,1.25,3.36c0,4.77,0.3,62.69,0.21,64.96"),
                    PathParser.createPathFromPathData("M35.97,21.78c2.97-0.11,30.42-3.03,32.62-3.2c3.96-0.31,4.97,0.67,4.97,5.05c0,1.51-0.06,40.88-0.06,61.5"),
                    PathParser.createPathFromPathData("M36.32,43.24c10.13-1.02,26.94-2.22,35.84-2.54"),
                    PathParser.createPathFromPathData("M36.42,65c8.83-0.75,26.71-2.29,35.86-2.34"),
                    PathParser.createPathFromPathData("M16.04,89.56c3.29,0.97,7.12,0.64,10.47,0.41C41.09,89,63.75,87.5,84.62,86.83c3.34-0.11,6.7,0.05,10.01,0.64")
                )
            )
        )
    }
}

class KanjiView(context: Context, private val path: List<Path>) : View(context) {

    private val paint = Paint()

    init {
        paint.strokeWidth = 3F
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.forEach{ canvas?.drawPath(it, paint) }
    }
}
