package com.itheamc.naxatestapp

import android.app.Application
import com.itheamc.naxatestapp.api.EntryService
import com.itheamc.naxatestapp.api.RetrofitHelper
import com.itheamc.naxatestapp.room_db.EntryDatabase
import com.itheamc.naxatestapp.repositories.MainRepository

class NaxaApplication : Application() {
    lateinit var mainRepository: MainRepository

    override fun onCreate() {
        super.onCreate()

        // Database initialization
        val database: EntryDatabase by lazy {
            EntryDatabase.instance(this)
        }

        // Retrofit service initialization
        val entryService: EntryService by lazy {
            RetrofitHelper.instance().create(EntryService::class.java)
        }

        mainRepository = MainRepository(entryDao = database.entryDao(), entryService = entryService)
    }
}