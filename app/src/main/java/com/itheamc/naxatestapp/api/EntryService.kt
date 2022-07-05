package com.itheamc.naxatestapp.api

import com.itheamc.naxatestapp.models.EntryList
import retrofit2.Response
import retrofit2.http.GET

interface EntryService {
    @GET("/entries")
    suspend fun getEntries(): Response<EntryList>

}