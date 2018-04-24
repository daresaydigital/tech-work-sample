package com.vp.weatherapp.data.local

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.vp.weatherapp.R
import com.vp.weatherapp.common.ext.ioThread
import com.vp.weatherapp.data.local.fts.CityGson
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
//            val time = measureTimeMillis {
                doInsertions(reader, db)
//            }
//            println("TIME = $time")
        }
//        ioThread {
//            val iterator = getIterator()
//            val time = measureTimeMillis { doInsertionsDsl(iterator, db) }
//            println("TIME = $time")
//        }
    }

//    private fun getIterator(): Iterator<CityJson> {
//        val input = ctx.resources.openRawResource(R.raw.city_list)
//        val dslJson = DslJson<Any>(Settings.withRuntime<Any>().includeServiceLoader())
//        return dslJson.iterateOver(CityJson::class.java, input)
//    }
//
//    private fun doInsertionsDsl(iterator: Iterator<CityJson>, db: SupportSQLiteDatabase) {
//        val sql = "INSERT INTO city (city_id, name, country, lat, lon) VALUES (?, ?, ?, ?, ?)"
//        val stmt = db.compileStatement(sql)
//        var city: CityJson
//
//        try {
//            db.beginTransaction()
//
//            while (iterator.hasNext()) {
//                city = iterator.next()
//                stmt.bindLong(1, city.id)
//                stmt.bindString(2, city.name)
//                stmt.bindString(3, city.country)
//                stmt.bindDouble(4, city.coord.lat)
//                stmt.bindDouble(5, city.coord.lon)
//                stmt.execute()
//                stmt.clearBindings()
//
//                count++
//                currentProgress = ((count / TOTAL) * 100).toInt()
//                if (currentProgress > lastUpdate) {
//                    listener?.onProgress(currentProgress)
//                    lastUpdate = currentProgress
//                }
//
//            }
//            db.setTransactionSuccessful()
//            listener?.onComplete()
//
//        } catch (ex: UnsupportedEncodingException) {
//            ex.printStackTrace()
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//        } finally {
//            db.endTransaction()
//        }
//
//    }

    private fun getReader(): JsonReader {
        val input = ctx.resources.openRawResource(R.raw.city_list)
        return JsonReader(InputStreamReader(input, "UTF-8"))
    }

    private fun doInsertions(reader: JsonReader, db: SupportSQLiteDatabase) {
        val sql = "INSERT INTO city (city_id, name, country, lat, lon) VALUES (?, ?, ?, ?, ?)"
        val stmt = db.compileStatement(sql)

        var city: CityGson
        try {
            reader.beginArray()
            val gson = GsonBuilder().create()

            db.beginTransaction()
            while (reader.hasNext()) {
                city = gson.fromJson<CityGson>(reader, CityGson::class.java)
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