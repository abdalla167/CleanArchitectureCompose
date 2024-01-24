package com.example.firstprojectcompose.data

import com.example.firstprojectcompose.data.di.IODispatcher
import com.example.firstprojectcompose.data.local.GymDAO
import com.example.firstprojectcompose.data.local.GymFavouretState
import com.example.firstprojectcompose.data.local.LocalGym
import com.example.firstprojectcompose.data.remot.GymsApiServec
import com.example.firstprojectcompose.domain.Gym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsReposatery @Inject constructor(
    val apiServec: GymsApiServec,
    private var gymDAO : GymDAO,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
    ) {



    suspend fun loadGyms() = withContext(ioDispatcher) {
        try {

            updateLocaleDatabase()

        } catch (ex: Exception) {
            if (gymDAO.getAll().isEmpty()) {
                throw Exception("Somthing is Wroing ")
            }
        }
    }

    suspend fun getGyms():List<Gym>
    {
        return withContext(ioDispatcher){

            return@withContext gymDAO.getAll().map {
                Gym(it.id,it.name,it.places,it.isOpend,it.isFavourite)
            }
        }
    }

    suspend fun updateLocaleDatabase() {
        val gyms = apiServec.getGyms()

        val favouriteGymsList = gymDAO.getFavouertGym()
        gymDAO.addAll(gyms.map {
            LocalGym(it.id,it.name,it.place,it.isOpen )
        })
        gymDAO.updateAll(
            favouriteGymsList.map {
                GymFavouretState(id = it.id, true)
            }
        )
    }

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(ioDispatcher)
        {
            gymDAO.update(GymFavouretState(id = gymId, isFavourite = state))
            return@withContext gymDAO.getAll()
        }

}