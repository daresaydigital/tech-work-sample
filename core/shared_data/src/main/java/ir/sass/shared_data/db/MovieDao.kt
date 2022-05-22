package ir.sass.shared_data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ir.sass.shared_data.db.model.RESULT_TABLE_NAME
import ir.sass.shared_data.db.model.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM $RESULT_TABLE_NAME")
    fun getAllResults(): Flow<List<ResultEntity>>

    @Insert
    fun insertNewResult(input : ResultEntity)

    @Delete
    fun deleteAResult(input : ResultEntity)
}