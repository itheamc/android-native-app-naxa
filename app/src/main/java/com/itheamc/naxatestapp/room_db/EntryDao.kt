package com.itheamc.naxatestapp.room_db

import androidx.room.*
import com.itheamc.naxatestapp.models.Entry

@Dao
interface EntryDao {
    /**
     * To get all the entries
     */
    @Query("SELECT * FROM entries ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun entries(limit: Int, offset: Int): List<Entry>

    /**
     * Function to insert [entry] to our database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry): Long

    /**
     * Function to delete [entry] from our database
     */
    @Delete
    suspend fun deleteEntry(entry: Entry)

    /**
     * Function to update [entry] from our database
     */
    @Update
    suspend fun updateEntry(entry: Entry)
}