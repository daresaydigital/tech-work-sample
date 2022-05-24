package ir.sass.shared_data.db

import androidx.room.Dao
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
    fun insertNewResult(input: ResultEntity)

    @Query("DELETE FROM $RESULT_TABLE_NAME WHERE id = :id")
    fun deleteAResult(id: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM $RESULT_TABLE_NAME WHERE id = :id)")
    fun exists(id: Int): Boolean
}