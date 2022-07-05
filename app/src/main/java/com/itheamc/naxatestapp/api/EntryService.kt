package com.itheamc.naxatestapp.api

import com.itheamc.naxatestapp.models.Entry
import retrofit2.Response
import retrofit2.http.GET

interface EntryService {
    @GET("/entries")
    suspend fun getEntries(): Response<List<Entry>>

}