package com.itheamc.naxatestapp.room_db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.itheamc.naxatestapp.NaxaApplication
import com.itheamc.naxatestapp.models.Entry

@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class EntryDatabase : RoomDatabase() {
    /**
     * Abstract function to return the instance of the Entry Dao
     */
    abstract fun entryDao(): EntryDao

    /**
     * Static variable and function
     */
    companion object {

        @Volatile
        private var INSTANCE: EntryDatabase? = null

        /**
         * Singleton function to return the instance of the database
         */
        fun instance(application: NaxaApplication): EntryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    EntryDatabase::class.java,
                    "a_entries"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}