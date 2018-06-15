package com.daresay.sayweather

import com.deresay.sayweather.models.Wind
import com.deresay.sayweather.utils.SayWeatherUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*


class SayWeatherUtilTest {
    @Test
    fun checkBackgroundColor() {
        val calendar = Calendar.getInstance()
        val diff = with(calendar) {
            get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + get(Calendar.MINUTE) * 60 * 1000 +
                    get(Calendar.SECOND) * 1000
        }
        val midNightMillis = calendar.timeInMillis - diff //Today midnight.

        assertEquals(SayWeatherUtil.timing(Date(midNightMillis)),
                SayWeatherUtil.TIMING.NIGHT)
        assertEquals(SayWeatherUtil.timing(Date(midNightMillis + 12 * 60 * 60 * 1000)),
                SayWeatherUtil.TIMING.DAY)
        assertEquals(SayWeatherUtil.timing(Date(midNightMillis + 17 * 60 * 60 * 1000)),
                SayWeatherUtil.TIMING.EVENING)
        assertEquals(SayWeatherUtil.timing(Date(midNightMillis + 9 * 60 * 60 * 1000)),
                SayWeatherUtil.TIMING.MORNING)
    }


    @Test
    fun testTemperatureText() {
        assertEquals(SayWeatherUtil.temperature(30), "30 \u2103")
        assertEquals(SayWeatherUtil.temperature(-1), "-1 \u2103")
    }

    @Test
    fun testHumidityText() {
        assertTrue(SayWeatherUtil.humidity(90) == "Humidity : 90 %")
    }

    @Test
    fun testWindText() {
        assertEquals(SayWeatherUtil.wind(Wind(120.5f, 5.4f)), "Wind : 5.4 mph SE")
    }

    @Test
    fun testWindDirection() {
        assertTrue(SayWeatherUtil.windDirection(0) == "N")
        assertTrue(SayWeatherUtil.windDirection(360) == "N")
        assertEquals(SayWeatherUtil.windDirection(90), "E")
        assertEquals(SayWeatherUtil.windDirection(270), "W")
        assert(SayWeatherUtil.windDirection(180) == "S")

        assertEquals(SayWeatherUtil.windDirection(45), "NE")
        assertEquals(SayWeatherUtil.windDirection(95), "SE")
        assertEquals(SayWeatherUtil.windDirection(195), "SW")
        assertEquals(SayWeatherUtil.windDirection(280), "NW")

        //Else condition.
        assertEquals(SayWeatherUtil.windDirection(500), "")
    }

}
