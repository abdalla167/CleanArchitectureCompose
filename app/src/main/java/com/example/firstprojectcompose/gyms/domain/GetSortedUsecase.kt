package com.example.firstprojectcompose.gyms.domain

import com.example.firstprojectcompose.gyms.data.GymsReposatery
import javax.inject.Inject

class GetSortedUsecase @Inject constructor(  private val gymsReposatery : GymsReposatery)  {


    suspend fun invoke () :List<Gym>
    {
        return gymsReposatery.getGyms().sortedBy { it.name }
    }

}