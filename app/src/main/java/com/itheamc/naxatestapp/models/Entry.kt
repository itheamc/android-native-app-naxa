package com.itheamc.naxatestapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val api: String?,
    val desc: String?,
    val auth: String?,
    val https: Boolean,
    val cors: String?,
    val link: String?,
    val category: String?
)
