package com.example.firstprojectcompose.domain

import com.example.firstprojectcompose.data.GymsReposatery
import javax.inject.Inject

class GetSortedUsecase @Inject constructor(  private val gymsReposatery : GymsReposatery)  {


    suspend operator fun invoke () :List<Gym>
    {
        return gymsReposatery.getGyms().sortedBy { it.name }
    }

}