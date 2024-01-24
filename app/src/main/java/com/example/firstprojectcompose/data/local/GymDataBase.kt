package com.example.firstprojectcompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalGym::class], version = 3, exportSchema = false)
abstract class GymDataBase : RoomDatabase()
{

    abstract val dao: GymDAO


}