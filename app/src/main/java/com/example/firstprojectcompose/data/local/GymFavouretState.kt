package com.example.firstprojectcompose.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gyms")
@Parcelize
data class GymFavouretState (
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @SerializedName("isFavourite")
    var isFavourite: Boolean = false
) : Parcelable