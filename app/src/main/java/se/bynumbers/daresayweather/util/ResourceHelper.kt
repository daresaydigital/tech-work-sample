package se.bynumbers.daresayweather.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.isDigitsOnly
import se.bynumbers.daresayweather.util.Constants.Companion.SP_DARESAY_WEATHER
import se.bynumbers.daresayweather.util.Constants.Companion.SP_FAHRENHEIT
import se.bynumbers.daresayweather.R
import kotlin.math.roundToInt

class ResourceHelper {

    companion object {
        private val TAG = "ResourceHelper"
        fun getWeatherDrawableFromMap(context: Context, key: String): Drawable? {

            Log.d(TAG, "Looking for resource with name: $key")
            val r = context.resources

            val packageName = context.packageName
            val i = r.getIdentifier(key, "drawable", packageName)

            return AppCompatResources.getDrawable(context, i)


        }
        fun setFahrenheit(on : Boolean, context : Context){
            val sp = context.getSharedPreferences(SP_DARESAY_WEATHER,
                MODE_PRIVATE
            )
            val editor = sp.edit()
            editor.putBoolean(SP_FAHRENHEIT, on)
            editor.apply()

        }
        fun toFahrenheit(temp : String) : String {
            if(temp.isEmpty())  return "0"
            val temp1 = temp.substringBefore(" ")

            //TODO check for digits only (but note minus degre
            if(temp1.isEmpty())  return "0"
            val fahrenheit = (temp1.toDouble() * 9)/5 + 32

            return fahrenheit.roundToInt().toString()
        }
        fun toCelsius(temp : String) : String {
            if(temp.isEmpty())  return "0"
            val temp1 = temp.substringBefore(" ")
            if(temp1.isEmpty() || !temp1.isDigitsOnly())  return "0"
            val celsius = ((temp1.toDouble()-32) * 5)/9

            return celsius.roundToInt().toString()

        }

        fun getFahrenheitOrCelsius(context: Context): String? {


         /*   val r = context.resources

            val packageName = context.packageName
            val sp = context.getSharedPreferences("SP_DARESAY_WEATHER", MODE_PRIVATE)
            val fahrenheit = sp.getBoolean("SP_FARENHEIT", false)

            var id = R.drawable.wi_celsius
            if (fahrenheit) {
                id = R.drawable.wi_fahrenheit
            }
            Log.d(TAG, "Farenheit or celsius: $id")
            return AppCompatResources.getDrawable(context, id)
*/          val sp = context.getSharedPreferences(SP_DARESAY_WEATHER, MODE_PRIVATE)
            val fahrenheit = sp.getBoolean(SP_FAHRENHEIT, false)

            var ret ="\u00B0C"
            if (fahrenheit) {
                ret = "\u00B0F"
            }
            return ret
        }
    }
}