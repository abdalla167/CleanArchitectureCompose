package com.example.firstprojectcompose.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalGym::class], version = 3, exportSchema = false)
abstract class GymDataBase : RoomDatabase()
{

    abstract val dao: GymDAO

    companion object
    {
        @Volatile
        private var DAOinstance : GymDAO?=null
        private fun buildDataBase(context: Context): GymDataBase
        {
         return Room.databaseBuilder(
             context.applicationContext,
             GymDataBase::class.java,
             "gyms_database"
         ).fallbackToDestructiveMigration().build()
        }
        fun getDAO(context: Context): GymDAO
        {
            synchronized(this)
            {
                if(DAOinstance ==null)
                    DAOinstance = buildDataBase(context).dao
                return DAOinstance as GymDAO
            }

        }
    }
}