package com.vp.weatherapp.ui.main.paging.indicators

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.vp.weatherapp.R
import com.vp.weatherapp.ui.main.paging.helpers.DimensionHelper
import java.util.*


class DotIndicator : RelativeLayout, SelectionIndicator {


    override val selectedItemIndex: Int
        get() = selectedDotIndex

    override var numberOfItems: Int
        get() = numberOfDots
        set(value) {
            numberOfDots = value
            reflectParametersInView()
        }

    override var transitionDuration: Int
        get() = dotTransitionDuration
        set(value) {
            dotTransitionDuration = value
            reflectParametersInView()
        }

    override fun setVisibility(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override val isVisible: Boolean
        get() = visibility == View.VISIBLE

    //

    private var numberOfDots: Int = 0

    private var selectedDotIndex: Int = 0

    var unselectedDotDiameter: Int = 0
        private set

    var selectedDotDiameter: Int = 0
        private set

    private var unselectedDotColor: Int = 0

    private var selectedDotColor: Int = 0

    var spacingBetweenDots: Int = 0
        private set

    private var dotTransitionDuration: Int = 0

    private val dots = ArrayList<Dot>()


    constructor(context: Context) : super(context) {
        init(null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs, defStyleAttr, defStyleRes)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DotIndicator, defStyleAttr, defStyleRes)

        val defaultSelectedDotDiameterPx = DimensionHelper.dpToPx(context, DEFAULT_SELECTED_DOT_DIAMETER_DP.toFloat())
        val defaultUnselectedDotDiameterPx = DimensionHelper.dpToPx(context, DEFAULT_UNSELECTED_DOT_DIAMETER_DP.toFloat())
        val defaultSpacingBetweenDotsPx = DimensionHelper.dpToPx(context, DEFAULT_SPACING_BETWEEN_DOTS_DP.toFloat())

        numberOfDots = attributes.getInt(R.styleable.DotIndicator_numberOfDots, DEFAULT_NUMBER_OF_DOTS)
        selectedDotIndex = attributes.getInt(R.styleable.DotIndicator_selectedDotIndex, DEFAULT_SELECTED_DOT_INDEX)
        unselectedDotDiameter = attributes.getDimensionPixelSize(R.styleable.DotIndicator_unselectedDotDiameter, defaultUnselectedDotDiameterPx)
        selectedDotDiameter = attributes.getDimensionPixelSize(R.styleable.DotIndicator_selectedDotDiameter, defaultSelectedDotDiameterPx)
        unselectedDotColor = attributes.getColor(R.styleable.DotIndicator_unselectedDotColor, DEFAULT_UNSELECTED_DOT_COLOR)
        selectedDotColor = attributes.getColor(R.styleable.DotIndicator_selectedDotColor, DEFAULT_SELECTED_DOT_COLOR)
        spacingBetweenDots = attributes.getDimensionPixelSize(R.styleable.DotIndicator_spacingBetweenDots, defaultSpacingBetweenDotsPx)
        dotTransitionDuration = attributes.getDimensionPixelSize(R.styleable.DotIndicator_dotTransitionDuration, DEFAULT_DOT_TRANSITION_DURATION_MS)

        attributes.recycle()

        layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        gravity = Gravity.CENTER
        reflectParametersInView()
    }

    private fun reflectParametersInView() {
        removeAllViews()
        dots.clear()

        for (i in 0 until numberOfDots) {
            val dot = Dot(context)
            dot.setInactiveDiameterPx(unselectedDotDiameter)
                    .setActiveDiameterPx(selectedDotDiameter)
                    .setActiveColor(selectedDotColor)
                    .setInactiveColor(unselectedDotColor)
                    .setTransitionDuration(dotTransitionDuration)

            if (i == selectedDotIndex) {
                dot.setActive(false)
            } else {
                dot.setInactive(false)
            }

            val maxDiameterDim = Math.max(selectedDotDiameter, unselectedDotDiameter)
            val startMargin = i * (spacingBetweenDots + unselectedDotDiameter)
            val params = RelativeLayout.LayoutParams(maxDiameterDim, maxDiameterDim)
            params.setMargins(startMargin, 0, 0, 0)

            dot.layoutParams = params
            addView(dot)

            dots.add(i, dot)
        }
    }

    fun redrawDots() {
        reflectParametersInView()
    }

    fun setUnselectedDotDiameterPx(unselectedDotDiameterPx: Int) {
        this.unselectedDotDiameter = unselectedDotDiameterPx
        reflectParametersInView()
    }

    fun setUnselectedDotDiameterDp(unselectedDotDiameterDp: Int) {
        val diameterPx = DimensionHelper.dpToPx(context, unselectedDotDiameterDp.toFloat())
        setUnselectedDotDiameterPx(diameterPx)
    }

    fun setSelectedDotDiameterPx(selectedDotDiameterPx: Int) {
        this.selectedDotDiameter = selectedDotDiameterPx
        reflectParametersInView()
    }

    fun setSelectedDotDiameterDp(selectedDotDiameterDp: Int) {
        val diameterPx = DimensionHelper.dpToPx(context, selectedDotDiameterDp.toFloat())
        setSelectedDotDiameterPx(diameterPx)
    }

    fun setUnselectedDotColor(unselectedDotColor: Int) {
        this.unselectedDotColor = unselectedDotColor
        reflectParametersInView()
    }

    fun getUnselectedDotColor(): Int {
        return unselectedDotColor
    }

    fun setSelectedDotColor(selectedDotColor: Int) {
        this.selectedDotColor = selectedDotColor
        reflectParametersInView()
    }

    fun getSelectedDotColor(): Int {
        return selectedDotColor
    }

    fun setSpacingBetweenDotsPx(spacingBetweenDotsPx: Int) {
        this.spacingBetweenDots = spacingBetweenDotsPx
        reflectParametersInView()
    }

    fun setSpacingBetweenDotsDp(spacingBetweenDotsDp: Int) {
        val spacingPx = DimensionHelper.dpToPx(context, spacingBetweenDotsDp.toFloat())
        setSpacingBetweenDotsPx(spacingPx)
    }

    override fun setSelectedItem(index: Int, animate: Boolean) {
        if (dots.size > 0) {
            try {
                if (selectedDotIndex < dots.size) {
                    dots[selectedDotIndex].setInactive(animate)
                }

                dots[index].setActive(animate)
            } catch (e: IndexOutOfBoundsException) {
                throw IndexOutOfBoundsException()
            }

            selectedDotIndex = index
        }
    }


    companion object {
        private val TAG = "[DotIndicator]"

        private val DEFAULT_NUMBER_OF_DOTS = 1

        private val DEFAULT_SELECTED_DOT_INDEX = 0

        private val DEFAULT_UNSELECTED_DOT_DIAMETER_DP = 6

        private val DEFAULT_SELECTED_DOT_DIAMETER_DP = 9

        private val DEFAULT_UNSELECTED_DOT_COLOR = Color.LTGRAY

        private val DEFAULT_SELECTED_DOT_COLOR = Color.WHITE

        private val DEFAULT_SPACING_BETWEEN_DOTS_DP = 7

        private val DEFAULT_DOT_TRANSITION_DURATION_MS = 200
    }
}