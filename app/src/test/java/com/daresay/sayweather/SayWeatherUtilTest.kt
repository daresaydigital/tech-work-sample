package com.daresay.sayweather

import com.deresay.sayweather.utils.SayWeatherUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class SayWeatherUtilTest {
    @Test
    fun checkBackgroundColor() {
        assertEquals(SayWeatherUtil.background(Date()),
                SayWeatherUtil.BACKGROUND_COLOR.NIGHT)
    }
}