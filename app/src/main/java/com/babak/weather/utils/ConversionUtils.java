package com.babak.weather.utils;

public final class ConversionUtils {
    private ConversionUtils() {
    }

    public static double celsiusToFahrenheit(double celsius) {
        return celsius * 9/5 + 32;
    }

    // Converts Open Weather Map weather id to corresponding weather icon name.
    public static String owmIdToIconName(int id, String icon) {
        String omwId = Integer.toString(id);
        omwId = omwId + (!icon.isEmpty() ? icon.charAt(icon.length() - 1) : "");
        String iconName = "";

        switch (omwId) {
            case "200d":
                iconName = "day_thunderstorm";
                break;
            case "201d":
                iconName = "day_thunderstorm";
                break;
            case "202d":
                iconName = "day_thunderstorm";
                break;
            case "210d":
                iconName = "day_lightning";
                break;
            case "211d":
                iconName = "day_lightning";
                break;
            case "212d":
                iconName = "day_lightning";
                break;
            case "221d":
                iconName = "day_lightning";
                break;
            case "230d":
                iconName = "day_thunderstorm";
                break;
            case "231d":
                iconName = "day_thunderstorm";
                break;
            case "232d":
                iconName = "day_thunderstorm";
                break;
            case "300d":
                iconName = "day_sprinkle";
                break;
            case "301d":
                iconName = "day_sprinkle";
                break;
            case "302d":
                iconName = "day_rain";
                break;
            case "310d":
                iconName = "day_rain";
                break;
            case "311d":
                iconName = "day_rain";
                break;
            case "312d":
                iconName = "day_rain";
                break;
            case "313d":
                iconName = "day_rain";
                break;
            case "314d":
                iconName = "day_rain";
                break;
            case "321d":
                iconName = "day_sprinkle";
                break;
            case "500d":
                iconName = "day_sprinkle";
                break;
            case "501d":
                iconName = "day_rain";
                break;
            case "502d":
                iconName = "day_rain";
                break;
            case "503d":
                iconName = "day_rain";
                break;
            case "504d":
                iconName = "day_rain";
                break;
            case "511d":
                iconName = "day_rain_mix";
                break;
            case "520d":
                iconName = "day_showers";
                break;
            case "521d":
                iconName = "day_showers";
                break;
            case "522d":
                iconName = "day_showers";
                break;
            case "531d":
                iconName = "day_storm_showers";
                break;
            case "600d":
                iconName = "day_snow";
                break;
            case "601d":
                iconName = "day_sleet";
                break;
            case "602d":
                iconName = "day_snow";
                break;
            case "611d":
                iconName = "day_rain_mix";
                break;
            case "612d":
                iconName = "day_rain_mix";
                break;
            case "615d":
                iconName = "day_rain_mix";
                break;
            case "616d":
                iconName = "day_rain_mix";
                break;
            case "620d":
                iconName = "day_rain_mix";
                break;
            case "621d":
                iconName = "day_snow";
                break;
            case "622d":
                iconName = "day_snow";
                break;
            case "701d":
                iconName = "day_showers";
                break;
            case "711d":
                iconName = "smoke";
                break;
            case "721d":
                iconName = "day_haze";
                break;
            case "731d":
                iconName = "dust";
                break;
            case "741d":
                iconName = "day_fog";
                break;
            case "761d":
                iconName = "dust";
                break;
            case "762d":
                iconName = "dust";
                break;
            case "781d":
                iconName = "tornado";
                break;
            case "800d":
                iconName = "day_sunny";
                break;
            case "801d":
                iconName = "day_cloudy_gusts";
                break;
            case "802d":
                iconName = "day_cloudy_gusts";
                break;
            case "803d":
                iconName = "day_cloudy_gusts";
                break;
            case "804d":
                iconName = "day_sunny_overcast";
                break;
            case "900d":
                iconName = "tornado";
                break;
            case "902d":
                iconName = "hurricane";
                break;
            case "903d":
                iconName = "snowflake_cold";
                break;
            case "904d":
                iconName = "hot";
                break;
            case "906d":
                iconName = "day_hail";
                break;
            case "957d":
                iconName = "strong_wind";
                break;
            case "200n":
                iconName = "night_alt_thunderstorm";
                break;
            case "201n":
                iconName = "night_alt_thunderstorm";
                break;
            case "202n":
                iconName = "night_alt_thunderstorm";
                break;
            case "210n":
                iconName = "night_alt_lightning";
                break;
            case "211n":
                iconName = "night_alt_lightning";
                break;
            case "212n":
                iconName = "night_alt_lightning";
                break;
            case "221n":
                iconName = "night_alt_lightning";
                break;
            case "230n":
                iconName = "night_alt_thunderstorm";
                break;
            case "231n":
                iconName = "night_alt_thunderstorm";
                break;
            case "232n":
                iconName = "night_alt_thunderstorm";
                break;
            case "300n":
                iconName = "night_alt_sprinkle";
                break;
            case "301n":
                iconName = "night_alt_sprinkle";
                break;
            case "302n":
                iconName = "night_alt_rain";
                break;
            case "310n":
                iconName = "night_alt_rain";
                break;
            case "311n":
                iconName = "night_alt_rain";
                break;
            case "312n":
                iconName = "night_alt_rain";
                break;
            case "313n":
                iconName = "night_alt_rain";
                break;
            case "314n":
                iconName = "night_alt_rain";
                break;
            case "321n":
                iconName = "night_alt_sprinkle";
                break;
            case "500n":
                iconName = "night_alt_sprinkle";
                break;
            case "501n":
                iconName = "night_alt_rain";
                break;
            case "502n":
                iconName = "night_alt_rain";
                break;
            case "503n":
                iconName = "night_alt_rain";
                break;
            case "504n":
                iconName = "night_alt_rain";
                break;
            case "511n":
                iconName = "night_alt_rain_mix";
                break;
            case "520n":
                iconName = "night_alt_showers";
                break;
            case "521n":
                iconName = "night_alt_showers";
                break;
            case "522n":
                iconName = "night_alt_showers";
                break;
            case "531n":
                iconName = "night_alt_storm_showers";
                break;
            case "600n":
                iconName = "night_alt_snow";
                break;
            case "601n":
                iconName = "night_alt_sleet";
                break;
            case "602n":
                iconName = "night_alt_snow";
                break;
            case "611n":
                iconName = "night_alt_rain_mix";
                break;
            case "612n":
                iconName = "night_alt_rain_mix";
                break;
            case "615n":
                iconName = "night_alt_rain_mix";
                break;
            case "616n":
                iconName = "night_alt_rain_mix";
                break;
            case "620n":
                iconName = "night_alt_rain_mix";
                break;
            case "621n":
                iconName = "night_alt_snow";
                break;
            case "622n":
                iconName = "night_alt_snow";
                break;
            case "701n":
                iconName = "night_alt_showers";
                break;
            case "711n":
                iconName = "smoke";
                break;
            case "721n":
                iconName = "day_haze";
                break;
            case "731n":
                iconName = "dust";
                break;
            case "741n":
                iconName = "night_fog";
                break;
            case "761n":
                iconName = "dust";
                break;
            case "762n":
                iconName = "dust";
                break;
            case "781n":
                iconName = "tornado";
                break;
            case "800n":
                iconName = "night_clear";
                break;
            case "801n":
                iconName = "night_alt_cloudy_gusts";
                break;
            case "802n":
                iconName = "night_alt_cloudy_gusts";
                break;
            case "803n":
                iconName = "night_alt_cloudy_gusts";
                break;
            case "804n":
                iconName = "night_alt_cloudy";
                break;
            case "900n":
                iconName = "tornado";
                break;
            case "902n":
                iconName = "hurricane";
                break;
            case "903n":
                iconName = "snowflake_cold";
                break;
            case "904n":
                iconName = "hot";
                break;
            case "906n":
                iconName = "night_alt_hail";
                break;
            case "957n":
                iconName = "strong_wind";
                break;
            case "200":
                iconName = "thunderstorm";
                break;
            case "201":
                iconName = "thunderstorm";
                break;
            case "202":
                iconName = "thunderstorm";
                break;
            case "210":
                iconName = "lightning";
                break;
            case "211":
                iconName = "lightning";
                break;
            case "212":
                iconName = "lightning";
                break;
            case "221":
                iconName = "lightning";
                break;
            case "230":
                iconName = "thunderstorm";
                break;
            case "231":
                iconName = "thunderstorm";
                break;
            case "232":
                iconName = "thunderstorm";
                break;
            case "300":
                iconName = "sprinkle";
                break;
            case "301":
                iconName = "sprinkle";
                break;
            case "302":
                iconName = "rain";
                break;
            case "310":
                iconName = "rain_mix";
                break;
            case "311":
                iconName = "rain";
                break;
            case "312":
                iconName = "rain";
                break;
            case "313":
                iconName = "showers";
                break;
            case "314":
                iconName = "rain";
                break;
            case "321":
                iconName = "sprinkle";
                break;
            case "500":
                iconName = "sprinkle";
                break;
            case "501":
                iconName = "rain";
                break;
            case "502":
                iconName = "rain";
                break;
            case "503":
                iconName = "rain";
                break;
            case "504":
                iconName = "rain";
                break;
            case "511":
                iconName = "rain_mix";
                break;
            case "520":
                iconName = "showers";
                break;
            case "521":
                iconName = "showers";
                break;
            case "522":
                iconName = "showers";
                break;
            case "531":
                iconName = "storm_showers";
                break;
            case "600":
                iconName = "snow";
                break;
            case "601":
                iconName = "snow";
                break;
            case "602":
                iconName = "sleet";
                break;
            case "611":
                iconName = "rain_mix";
                break;
            case "612":
                iconName = "rain_mix";
                break;
            case "615":
                iconName = "rain_mix";
                break;
            case "616":
                iconName = "rain_mix";
                break;
            case "620":
                iconName = "rain_mix";
                break;
            case "621":
                iconName = "snow";
                break;
            case "622":
                iconName = "snow";
                break;
            case "701":
                iconName = "showers";
                break;
            case "711":
                iconName = "smoke";
                break;
            case "721":
                iconName = "day_haze";
                break;
            case "731":
                iconName = "dust";
                break;
            case "741":
                iconName = "fog";
                break;
            case "761":
                iconName = "dust";
                break;
            case "762":
                iconName = "dust";
                break;
            case "771":
                iconName = "cloudy_gusts";
                break;
            case "781":
                iconName = "tornado";
                break;
            case "800":
                iconName = "day_sunny";
                break;
            case "801":
                iconName = "cloudy_gusts";
                break;
            case "802":
                iconName = "cloudy_gusts";
                break;
            case "803":
                iconName = "cloudy_gusts";
                break;
            case "804":
                iconName = "cloudy";
                break;
            case "900":
                iconName = "tornado";
                break;
            case "901":
                iconName = "storm_showers";
                break;
            case "902":
                iconName = "hurricane";
                break;
            case "903":
                iconName = "snowflake_cold";
                break;
            case "904":
                iconName = "hot";
                break;
            case "905":
                iconName = "windy";
                break;
            case "906":
                iconName = "hail";
                break;
            case "957":
                iconName = "strong_wind";
                break;
            default:
                return iconName = "why_are_you_here";
        }
        iconName = "wi_" + iconName;
        return iconName;
    }
}
