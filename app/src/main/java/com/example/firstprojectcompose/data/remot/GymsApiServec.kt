package com.example.firstprojectcompose.data.remot

import com.example.firstprojectcompose.domain.Gym
import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiServec {

    @GET("gyms.json")
   suspend fun getGyms(): List<RemotGym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(
        @Query("equalTo") id: Int
    ): Map<String, RemotGym>

}