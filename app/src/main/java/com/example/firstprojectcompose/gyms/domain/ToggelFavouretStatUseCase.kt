package com.example.firstprojectcompose.gyms.domain

import com.example.firstprojectcompose.gyms.data.GymsReposatery
import javax.inject.Inject

class ToggelFavouretStatUseCase @Inject constructor(
    private val gymsReposatery: GymsReposatery,
    private val getSortedUsecase: GetInitialGymsAllUseCAse
) {

    suspend fun invoke(id: Int, oldValue: Boolean): List<Gym> {

        val newState = oldValue.not()
        gymsReposatery.toggleFavouriteGym(id, newState).sortedBy { it.name }
        return getSortedUsecase.Invoke()
    }


}