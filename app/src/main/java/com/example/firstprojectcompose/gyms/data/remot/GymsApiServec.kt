package com.example.firstprojectcompose.gyms.data.remot

import com.example.firstprojectcompose.gyms.domain.Gym
import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiServec {

    @GET("gyms.json")
   suspend fun getGyms(): List<RemotGym>

   @GET("gyms.json?orderBy=\"id\"")
   suspend fun getSingelGym( @Query("equalTo") id:Int):Map<String, Gym>

}