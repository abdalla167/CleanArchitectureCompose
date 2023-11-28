package com.example.firstprojectcompose.gyms.domain

import com.example.firstprojectcompose.gyms.data.GymsReposatery

class GetSortedUsecase  {
    private val gymsReposatery = GymsReposatery()

    suspend fun invoke () :List<Gym>
    {
        return gymsReposatery.getGyms().sortedBy { it.name }
    }

}