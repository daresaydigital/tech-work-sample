package com.daresaydigital.data.utils

import android.util.Log
import androidx.annotation.RawRes
import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream

object JsonReader {
  fun getJson(@RawRes rawFile: Int): String {
    return try {
      val context = InstrumentationRegistry.getInstrumentation().context
      val jsonStream: InputStream = context.resources.openRawResource(rawFile)
      String(jsonStream.readBytes())
    } catch (exception: IOException) {
      Log.e(exception.message, "Error reading network response json asset")
      throw exception
    }
  }
}