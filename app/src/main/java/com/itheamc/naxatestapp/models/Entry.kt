package com.itheamc.naxatestapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName(value = "API")
    val api: String?,

    @SerializedName(value = "Description")
    val desc: String?,

    @SerializedName(value = "Auth")
    val auth: String?,

    @SerializedName(value = "HTTPS")
    val https: Boolean,

    @SerializedName(value = "Cors")
    val cors: String?,

    @SerializedName(value = "Link")
    val link: String?,

    @SerializedName(value = "Category")
    val category: String?
)



