package com.vp.weatherapp.ui.main.paging.indicators

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import com.vp.weatherapp.R
import com.vp.weatherapp.ui.main.paging.helpers.ColorHelper
import com.vp.weatherapp.ui.main.paging.helpers.DimensionHelper


class Dot : RelativeLayout {

    var inactiveDiameter: Int = 0
        private set

    var activeDiameter: Int = 0
        private set

    private var inactiveColor: Int = 0

    private var activeColor: Int = 0

    private var transitionDurationMs: Int = 0

    protected var currentState: Dot.State? = null
        private set

    private var shape: ShapeDrawable? = null

    private var drawableHolder: ImageView? = null

    private var currentAnimator: AnimatorSet? = null

    protected val currentDiameter: Int
        get() = shape!!.intrinsicHeight

    protected val currentColor: Int
        get() = shape!!.paint.color

    protected val defaultInactiveDiameterDp: Int
        get() = DEFAULT_INACTIVE_DIAMETER_DP

    protected val defaultActiveDiameterDp: Int
        get() = DEFAULT_ACTIVE_DIAMETER_DP

    protected val defaultInactiveColor: Int
        get() = DEFAULT_INACTIVE_COLOR

    protected val defaultActiveColor: Int
        get() = DEFAULT_ACTIVE_COLOR

    protected val defaultTransitionDuration: Int
        get() = DEFAULT_TRANSITION_DURATION_MS

    protected val defaultInitiallyActive: Boolean
        get() = DEFAULT_INITIALLY_ACTIVE

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
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Dot, defStyleAttr, defStyleRes)

        val defaultActiveDiameterPx = DimensionHelper.dpToPx(context, DEFAULT_ACTIVE_DIAMETER_DP.toFloat())
        val defaultInactiveDiameterPx = DimensionHelper.dpToPx(context, DEFAULT_INACTIVE_DIAMETER_DP.toFloat())

        inactiveDiameter = attributes.getDimensionPixelSize(R.styleable.Dot_inactiveDiameter, defaultInactiveDiameterPx)
        activeDiameter = attributes.getDimensionPixelSize(R.styleable.Dot_activeDiameter, defaultActiveDiameterPx)
        inactiveColor = attributes.getColor(R.styleable.Dot_inactiveColor, DEFAULT_INACTIVE_COLOR)
        activeColor = attributes.getColor(R.styleable.Dot_activeColor, DEFAULT_ACTIVE_COLOR)
        transitionDurationMs = attributes.getInt(R.styleable.Dot_transitionDuration, DEFAULT_TRANSITION_DURATION_MS)
        currentState = if (attributes.getBoolean(R.styleable.Dot_initiallyActive, DEFAULT_INITIALLY_ACTIVE))
            Dot.State.ACTIVE
        else
            Dot.State.INACTIVE

        attributes.recycle()

        reflectParametersInView()
    }

    private fun reflectParametersInView() {
        removeAllViews()

        val maxDimension = Math.max(inactiveDiameter, activeDiameter)
        layoutParams = RelativeLayout.LayoutParams(maxDimension, maxDimension)

        gravity = Gravity.CENTER

        val diameter = if (currentState == Dot.State.ACTIVE) activeDiameter else inactiveDiameter
        val color = if (currentState == Dot.State.ACTIVE) activeColor else inactiveColor
        shape = ShapeDrawable(OvalShape())
        shape!!.intrinsicWidth = diameter
        shape!!.intrinsicHeight = diameter
        shape!!.paint.color = color

        drawableHolder = ImageView(context)
        drawableHolder!!.setImageDrawable(null) // Forces redraw
        drawableHolder!!.setImageDrawable(shape)

        addView(drawableHolder)
    }

    private fun animateDotChange(startSize: Int, endSize: Int, startColor: Int,
                                 endColor: Int, duration: Int) {
        if (startSize < 0) {
            throw IllegalArgumentException("startSize cannot be less than 0")
        } else if (endSize < 0) {
            throw IllegalArgumentException("endSize cannot be less than 0")
        } else if (duration < 0) {
            throw IllegalArgumentException("duration cannot be less than 0")
        }

        if (currentAnimator != null) {
            currentAnimator!!.cancel()
        }

        currentAnimator = AnimatorSet()
        currentAnimator!!.duration = duration.toLong()
        currentAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                if (currentState == Dot.State.INACTIVE) {
                    currentState = Dot.State.TRANSITIONING_TO_ACTIVE
                } else if (currentState == Dot.State.ACTIVE) {
                    currentState = Dot.State.TRANSITIONING_TO_INACTIVE
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                if (!currentState!!.isStable) {
                    currentState = currentState!!.transitioningTo()
                }

                changeSize(endSize)
                changeColor(endColor)

                currentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                if (!currentState!!.isStable) {
                    currentState = currentState!!.transitioningFrom()
                }

                changeSize(startSize)
                changeColor(startColor)

                currentAnimator = null
            }
        })

        val transitionSize = ValueAnimator.ofInt(startSize, endSize)
        transitionSize.addUpdateListener { animation ->
            val size = animation.animatedValue as Int
            changeSize(size)
        }

        val transitionColor = ValueAnimator.ofFloat(0f, 1f)
        transitionColor.addUpdateListener { animation ->
            val mixValue = animation.animatedValue as Float
            changeColor(ColorHelper.blendColors(startColor, endColor, mixValue))
        }

        currentAnimator!!.playTogether(transitionSize, transitionColor)
        currentAnimator!!.start()
    }

    private fun changeSize(newSizePx: Int) {
        shape!!.intrinsicWidth = newSizePx
        shape!!.intrinsicHeight = newSizePx
        drawableHolder!!.setImageDrawable(null) // Forces ImageView to update drawable
        drawableHolder!!.setImageDrawable(shape)
    }

    private fun changeColor(newColor: Int) {
        shape!!.paint.color = newColor
    }

    fun setInactiveDiameterPx(inactiveDiameterPx: Int): Dot {
        if (inactiveDiameterPx < 0) {
            throw IllegalArgumentException("inactiveDiameterPx cannot be less than 0")
        }

        this.inactiveDiameter = inactiveDiameterPx
        reflectParametersInView()
        return this
    }

    fun setInactiveDiameterDp(inactiveDiameterDp: Int): Dot {
        if (inactiveDiameterDp < 0) {
            throw IllegalArgumentException("inactiveDiameterDp cannot be less than 0")
        }

        setInactiveDiameterPx(DimensionHelper.dpToPx(context, inactiveDiameterDp.toFloat()))
        return this
    }

    fun setActiveDiameterPx(activeDiameterPx: Int): Dot {
        if (activeDiameterPx < 0) {
            throw IllegalArgumentException("activeDiameterPx cannot be less than 0")
        }

        this.activeDiameter = activeDiameterPx
        reflectParametersInView()
        return this
    }

    fun setActiveDiameterDp(activeDiameterDp: Int): Dot {
        if (activeDiameterDp < 0) {
            throw IllegalArgumentException("activeDiameterDp cannot be less than 0")
        }

        setActiveDiameterPx(activeDiameterDp)
        return this
    }

    fun setInactiveColor(inactiveColor: Int): Dot {
        this.inactiveColor = inactiveColor
        reflectParametersInView()
        return this
    }

    fun getInactiveColor(): Int {
        return inactiveColor
    }

    fun setActiveColor(activeColor: Int): Dot {
        this.activeColor = activeColor
        reflectParametersInView()
        return this
    }

    fun getActiveColor(): Int {
        return activeColor
    }

    fun setTransitionDuration(transitionDurationMs: Int): Dot {
        if (transitionDurationMs < 0) {
            throw IllegalArgumentException("transitionDurationMs cannot be less than 0")
        }

        this.transitionDurationMs = transitionDurationMs
        return this
    }

    fun getTransitionDuration(): Int {
        return transitionDurationMs
    }

    fun toggleState(animate: Boolean) {
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
        }

        if (currentState != Dot.State.ACTIVE) {
            setActive(animate)
        } else if (currentState != Dot.State.INACTIVE) {
            setInactive(animate)
        } else {
            Log.e(TAG, "[Animation trying to start from illegal state]")
        }
    }

    fun setInactive(animate: Boolean) {
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
        }

        val shouldAnimate = animate && currentState != Dot.State.INACTIVE && transitionDurationMs > 0

        if (shouldAnimate) {
            animateDotChange(activeDiameter, inactiveDiameter, activeColor, inactiveColor,
                    transitionDurationMs)
        } else {
            changeSize(inactiveDiameter)
            changeColor(inactiveColor)
            currentState = Dot.State.INACTIVE
        }
    }

    fun setActive(animate: Boolean) {
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
        }

        val shouldAnimate = animate && currentState != Dot.State.ACTIVE && transitionDurationMs > 0


        if (shouldAnimate) {
            animateDotChange(inactiveDiameter, activeDiameter, inactiveColor, activeColor,
                    transitionDurationMs)
        } else {
            changeSize(activeDiameter)
            changeColor(activeColor)
            currentState = Dot.State.ACTIVE
        }
    }

    enum class State constructor(val isStable: Boolean, private val to: Dot.State?, private val from: Dot.State?) {
        INACTIVE(true, null, null),

        ACTIVE(true, null, null),

        TRANSITIONING_TO_ACTIVE(false, ACTIVE, INACTIVE),

        TRANSITIONING_TO_INACTIVE(false, INACTIVE, ACTIVE);

        fun transitioningTo(): Dot.State? {
            return to
        }

        fun transitioningFrom(): Dot.State? {
            return from
        }
    }

    companion object {

        private val TAG = "[Dot]"

        private val DEFAULT_INACTIVE_DIAMETER_DP = 6

        private val DEFAULT_ACTIVE_DIAMETER_DP = 9

        private val DEFAULT_INACTIVE_COLOR = Color.WHITE

        private val DEFAULT_ACTIVE_COLOR = Color.WHITE

        private val DEFAULT_TRANSITION_DURATION_MS = 200

        private val DEFAULT_INITIALLY_ACTIVE = false
    }
}