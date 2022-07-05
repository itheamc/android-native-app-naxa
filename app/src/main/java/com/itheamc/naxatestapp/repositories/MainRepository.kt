package com.itheamc.naxatestapp.repositories

import androidx.annotation.WorkerThread
import com.itheamc.naxatestapp.api.EntryService
import com.itheamc.naxatestapp.models.Entry
import com.itheamc.naxatestapp.room_db.EntryDao

class MainRepository(
    val entryDao: EntryDao,
    private val entryService: EntryService
) {
    suspend fun abc() {
        entryService.getEntries();
    }

    //    val entries: List<Entry> = entryDao.entries()


    @WorkerThread
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    @WorkerThread
    suspend fun deleteEntry(entry: Entry) {
        entryDao.deleteEntry(entry)
    }

    @WorkerThread
    suspend fun updateEntry(entry: Entry) {
        entryDao.updateEntry(entry)
    }
}