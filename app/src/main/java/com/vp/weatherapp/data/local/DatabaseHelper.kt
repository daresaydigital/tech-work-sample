package com.vp.weatherapp.data.local

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.ioThread
import com.vp.weatherapp.data.local.fts.CityJson
import com.vp.weatherapp.ui.initial.ProgressListener
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException


class DatabaseHelper(private val ctx: Application) : RoomDatabase.Callback() {

    companion object {
        const val TOTAL = 209579.0
    }

    var count = 0
    var currentProgress = 0
    var lastUpdate = 0

    var listener: ProgressListener? = null

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        ioThread {
            val reader = getReader()
            doInsertions(reader, db)
        }
    }

    private fun getReader(): JsonReader {
        val input = ctx.resources.openRawResource(R.raw.city_list)
        return JsonReader(InputStreamReader(input, "UTF-8"))
    }

    private fun doInsertions(reader: JsonReader, db: SupportSQLiteDatabase) {
        val sql = "INSERT INTO city (city_id, name, country, lat, lon) VALUES (?, ?, ?, ?, ?)"
        val stmt = db.compileStatement(sql)

        try {
            reader.beginArray()
            val gson = GsonBuilder().create()

            db.beginTransaction()
            while (reader.hasNext()) {
                val city = gson.fromJson<CityJson>(reader, CityJson::class.java)
                stmt.bindLong(1, city.id)
                stmt.bindString(2, city.name)
                stmt.bindString(3, city.country)
                stmt.bindDouble(4, city.coord.lat)
                stmt.bindDouble(5, city.coord.lon)
                stmt.execute()
                stmt.clearBindings()

                count++
                currentProgress = ((count / TOTAL) * 100).toInt()
                if (currentProgress > lastUpdate) {
                    listener?.onProgress(currentProgress)
                    lastUpdate = currentProgress
                }
            }
            db.setTransactionSuccessful()
            listener?.onComplete()

        } catch (ex: UnsupportedEncodingException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            db.endTransaction()
            reader.close()
        }

    }

}