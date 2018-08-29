package com.example.gustens.darsey_arbetsprov_kotlin.ui.windDirectionInd

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.gustens.darsey_arbetsprov_kotlin.R
import com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen.MainActivity
import kotlinx.android.synthetic.main.wind_dir_indicator.view.*

class WindDirIndView: RelativeLayout
{

    private val LOG_TAG = MainActivity::class.java.name

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?,    defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.wind_dir_indicator, this)

        // val imageView: ImageView = findViewById(R.id.image)
        // val textView: TextView = findViewById(R.id.caption)

        /*
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BenefitView)
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle()
        */

    }


    fun setWind( degreeOld : Float, degreeNew : Float) {

        Log.e(LOG_TAG, "setWind")
        val imageTest : ImageView = image_wind_direction
        val rotate : RotateAnimation = RotateAnimation(degreeOld, degreeNew, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        rotate.setDuration(500)
        rotate.setFillAfter(true);
        imageTest.startAnimation(rotate)
    }

    fun setTextSize(testSize :Float){

        val mTextSize: Float = testSize
        val textViewE: TextView = textView_east
        val textViewS: TextView = textView_south
        val textViewN: TextView = textView_nord
        val textViewW: TextView = textView_west

        val textViewArr : Array<TextView> = arrayOf (textViewE, textViewS,textViewN,textViewW)

        textViewArr.forEach { item -> item.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize) }


    }
}