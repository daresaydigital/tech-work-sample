package com.robledo.weatheri.utils;

public class MathUtils {

    /**
     * Get first digit of a number. 80032 => 8
     * @param num
     * @return
     */
    public static int getFirstDigit(int num)
    {
        if(num/10 == 0)
            return num;
        return getFirstDigit(num/10);

    }
}
