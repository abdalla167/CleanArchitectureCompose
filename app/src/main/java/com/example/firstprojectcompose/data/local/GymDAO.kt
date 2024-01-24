package com.example.firstprojectcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymDAO {

    @Query("SELECT * FROM gyms")
    suspend fun getAll():List<LocalGym>

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms:List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun update(gymfavouertStat: GymFavouretState)


    @Query("SELECT * FROM gyms WHERE isFavourite = 1")
    suspend fun getFavouertGym():List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymfavouertStat:List<GymFavouretState>)

}