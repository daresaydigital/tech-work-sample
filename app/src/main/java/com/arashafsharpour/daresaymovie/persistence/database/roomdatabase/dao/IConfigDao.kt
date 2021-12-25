package com.arashafsharpour.daresaymovie.persistence.database.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config

@Dao
interface IConfigDao {
    @Query("SELECT * FROM Config LIMIT 1;")
    suspend fun getConfig(): Config?

    @Insert(onConflict = REPLACE)
    fun save(config: Config)

    @Query("DELETE FROM Config;")
    fun clear()
}