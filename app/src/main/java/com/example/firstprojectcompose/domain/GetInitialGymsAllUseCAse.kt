package com.example.firstprojectcompose.domain

import com.example.firstprojectcompose.data.GymsReposatery
import javax.inject.Inject


class GetInitialGymsAllUseCAse @Inject constructor(
    private val gymsReposatery: GymsReposatery,
    private val getSortedUsecase: GetSortedUsecase
) {

    suspend fun Invoke(): List<Gym> {
        gymsReposatery.loadGyms()
        return getSortedUsecase.invoke()
    }
}