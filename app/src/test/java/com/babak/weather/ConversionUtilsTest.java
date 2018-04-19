package com.babak.weather;

import com.babak.weather.utils.ConversionUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ConversionUtilsTest {
    @Test
    public void testCelsiusToFahrenheit() {
        assertEquals(ConversionUtils.celsiusToFahrenheit(0.0), 32.0, 0.1);
        assertEquals(ConversionUtils.celsiusToFahrenheit(100.0), 212.0, 0.1);
        assertEquals(ConversionUtils.celsiusToFahrenheit(-20.0), -4.0, 0.1);
        assertEquals(ConversionUtils.celsiusToFahrenheit(25.3), 77.54, 0.1);
    }

    @Test
    public void testOwmIdToIconName() {
        assertEquals(ConversionUtils.owmIdToIconName(804, "04n"), "wi_night_alt_cloudy");
        assertEquals(ConversionUtils.owmIdToIconName(500, "10d"), "wi_day_sprinkle");
        assertEquals(ConversionUtils.owmIdToIconName(311, ""), "wi_rain");
        assertEquals(ConversionUtils.owmIdToIconName(1000, "04n"), "why_are_you_here");
        assertEquals(ConversionUtils.owmIdToIconName(804, "abc"), "why_are_you_here");
    }
}
