package com.example.firstprojectcompose.gyms.domain

import com.example.firstprojectcompose.gyms.data.GymsReposatery

class GetInitialGymsAllUseCAse {
    private val gymsReposatery = GymsReposatery()
    private val getSortedUsecase = GetSortedUsecase()
    suspend  fun Invoke() : List<Gym>
    {
        gymsReposatery.loadGyms()
        return getSortedUsecase.invoke()
    }
 }