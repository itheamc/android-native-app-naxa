package com.itheamc.naxatestapp.room_db

import androidx.room.*
import com.itheamc.naxatestapp.models.Entry

@Dao
interface EntryDao {
    /**
     * To get all the entries
     * [limit] -> size of entries to be fetched
     * [offset] -> offset is the point after which rows not more than limit is fetched
     */
    @Query("SELECT * FROM entries ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun entries(limit: Int, offset: Int): List<Entry>

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

    /**
     * Function to find the entry with given [link] from our database
     */
    @Query("SELECT * FROM entries WHERE link LIKE :link LIMIT 1")
    suspend fun findByLink(link: String): Entry?

    /**
     * Function to count the entries in our database
     */
    @Query("SELECT COUNT(*) FROM entries")
    suspend fun count(): Long
}