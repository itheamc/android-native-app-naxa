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


    /**
     * For database operation
     */
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

    @WorkerThread
    suspend fun findByLink(link: String): Entry? {
        return entryDao.findByLink(link)
    }


    /**
     * For Api call
     */
    suspend fun fetchEntries() {

        val response = entryService.getEntries()

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                if (entryDao.count() < it.count) {
                    it.entries.forEach { entry ->
                        if (findByLink(entry.link ?: "") == null) {
                            insertEntry(entry)
                        }
                    }
                }
            }
        }
    }
}