package com.arashafsharpour.daresaymovie.persistence.database.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config
import com.arashafsharpour.daresaymovie.persistence.database.roomdatabase.dao.IConfigDao

//@Database(entities = [], version = 1)
//@TypeConverters(RoomConverters::class)
abstract class DaresayMovieDatabase: RoomDatabase() {
//    abstract val configDao: IConfigDao
}