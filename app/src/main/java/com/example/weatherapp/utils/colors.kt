package com.example.weatherapp.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.ColorUtils.colorToHSL


@ColorInt
private fun hslToColor(@Size(3) hsl: FloatArray): Int {
    val r: Float
    val g: Float
    val b: Float

    val h = hsl[0]
    val s = hsl[1]
    val l = hsl[2]

    if (s == 0f) {
        b = l
        g = b
        r = g
    } else {
        val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
        val p = 2 * l - q
        r = hue2rgb(p, q, h + 1f / 3)
        g = hue2rgb(p, q, h)
        b = hue2rgb(p, q, h - 1f / 3)
    }

    return Color.rgb((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
}

private fun hue2rgb(p: Float, q: Float, t: Float): Float {
    var valueT = t
    if (valueT < 0) valueT += 1f
    if (valueT > 1) valueT -= 1f
    if (valueT < 1f / 6) return p + (q - p) * 6f * valueT
    if (valueT < 1f / 2) return q
    return if (valueT < 2f / 3) p + (q - p) * (2f / 3 - valueT) * 6f else p
}


fun colorToHSL(@ColorInt color: Int): FloatArray {
    val outFloatArray = FloatArray(3)
    colorToHSL(color, outFloatArray)
    return outFloatArray
}

@ColorInt
fun lightenColor(
    @ColorInt color: Int,
    value: Float
): Int {
    val hsl = colorToHSL(color)
    hsl[2] += value
    hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
    return hslToColor(hsl)
}

@ColorInt
fun darkenColor(@ColorInt color: Int,
                value: Float): Int {
    val hsl = colorToHSL(color)
    hsl[2] -= value
    hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
    return hslToColor(hsl)
}