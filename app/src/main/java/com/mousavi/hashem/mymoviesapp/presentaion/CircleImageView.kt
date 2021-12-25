package com.mousavi.hashem.mymoviesapp.presentaion

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : AppCompatImageView(context, attributeSet) {

    private val pathToClip = Path()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(pathToClip)
        super.onDraw(canvas)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            pathToClip.reset()

            pathToClip.addCircle(
                w / 2f,
                h / 2f,
                w / 2f,
                Path.Direction.CCW
            )
        }
    }
}