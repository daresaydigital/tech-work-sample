package com.babak.weather;

import com.babak.weather.utils.ConversionUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ConversionUtilsTest {
    @Test
    public void testOwmIdToIconName() {
        assertEquals(ConversionUtils.owmIdToIconName(804, "04n"), "wi_night_alt_cloudy");
        assertEquals(ConversionUtils.owmIdToIconName(500, "10d"), "wi_day_sprinkle");
        assertEquals(ConversionUtils.owmIdToIconName(311, ""), "wi_rain");
        assertEquals(ConversionUtils.owmIdToIconName(1000, "04n"), "why_are_you_here");
        assertEquals(ConversionUtils.owmIdToIconName(804, "abc"), "why_are_you_here");
    }
}
