package com.example.firstprojectcompose.gyms.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gyms")
@Parcelize
data class LocalGym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "gym_name")
    val name: String,
    @ColumnInfo(name = "gym_location")
    val places: String,
    val isOpend:Boolean,
    val isFavourite: Boolean = false
) : Parcelable