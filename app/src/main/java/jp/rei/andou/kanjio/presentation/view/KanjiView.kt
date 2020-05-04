package jp.rei.andou.kanjio.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.view.View

class KanjiView(context: Context, private val path: Path) : View(context) {

    private val paint = Paint()

    init {
        paint.strokeWidth = 20F
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(10F, 10F, 0F, 0F)
        path.transform(scaleMatrix)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }
}

/*todo private fun parsePathsDatas(pathDatas: String): Path {
       val newPath = Path()
       pathDatas.split("|").map {PathParser.createPathFromPathData(it)}.forEach(newPath::addPath)
       return newPath
   }*/