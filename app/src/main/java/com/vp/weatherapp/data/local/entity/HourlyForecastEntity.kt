package com.vp.weatherapp.data.local.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE


@Entity(tableName = "hourly_forecast",
        indices = [(Index("city_id"))],
        foreignKeys = [ForeignKey(
                entity = CityEntity::class,
                parentColumns = ["city_id"],
                childColumns = ["city_id"],
                onDelete = CASCADE)])
class HourlyForecastEntity @Ignore constructor(
        @PrimaryKey(autoGenerate = true)    var id:         Long    = 0,
        @ColumnInfo(name = "city_id")       var cityId:     Long    = 0,
        @ColumnInfo(name = "dt")            var dt:         Long    = 0,
        @ColumnInfo(name = "temp")          var temp:       Double  = 0.0,
        @ColumnInfo(name = "temp_low")      var tempLow:    Double  = 0.0,
        @ColumnInfo(name = "temp_high")     var tempHigh:   Double  = 0.0,
        @ColumnInfo(name = "icon")          var icon:       String  = "",
        @ColumnInfo(name = "description")   var description:String  = ""
) {
    constructor(): this(0,0, 0, 0.0, 0.0, 0.0, "", "")
}


// TODO: experiment
//: KParcelable {
//    private constructor(parcel: Parcel) : this(
//        parcel.readLong(),
//        parcel.readLong(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readDouble(),
//        parcel.readDouble(),
//        parcel.readDouble(),
//        parcel.readString()
//    )
//
//    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
//        writeValue(id)
//        writeValue(dt)
//        writeString(city)
//        writeString(country)
//        writeValue(temp)
//        writeValue(tempLow)
//        writeValue(tempHigh)
//        writeString(icon)
//    }
//
//    companion object {
//        @JvmField val CREATOR = parcelableCreator(::HourlyForecastEntity)
//    }
//}