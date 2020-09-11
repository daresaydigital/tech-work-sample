package se.bynumbers.daresayweather.util


import java.time.LocalDateTime

class DateTimeHelper {
    companion object {
        fun isDayTime() : Boolean {
            val now : LocalDateTime = LocalDateTime.now()
            if(now.hour > 6 && now.hour < 18){
                return true
            }
            return false
        }
    }
}