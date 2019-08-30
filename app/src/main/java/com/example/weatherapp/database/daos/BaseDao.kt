package com.example.weatherapp.database.daos

import androidx.room.*

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T) : Long

    @Transaction
    fun insertOrUpdate(obj: T) {
        val result = insert(obj)
        if (result == -1L) {
            update(obj)
        }
    }

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(vararg obj: T)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)

    /**
     *insert an arrayList of objects into the database
     *
     * @param list the list of objects to be inserted
     *
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( list: List<T>?)

    @Update
    fun updateAll( list: List<T>)
}