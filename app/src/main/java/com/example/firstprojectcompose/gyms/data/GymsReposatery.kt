package com.example.firstprojectcompose.gyms.data

import com.example.firstprojectcompose.GymApplication
import com.example.firstprojectcompose.gyms.data.local.GymDataBase
import com.example.firstprojectcompose.gyms.data.local.GymFavouretState
import com.example.firstprojectcompose.gyms.data.local.LocalGym
import com.example.firstprojectcompose.gyms.data.remot.GymsApiServec
import com.example.firstprojectcompose.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsReposatery {


    val apiServec: GymsApiServec =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cairo-gyms-822da-default-rtdb.firebaseio.com/")
            .build()
            .create(GymsApiServec::class.java)

    private var gymDAO = GymDataBase.getDAO(
        GymApplication.getApplicationContext()
    )

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
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
        return withContext(Dispatchers.IO){

            return@withContext gymDAO.getAll().map {
                Gym(it.id,it.name,it.places,it.isOpend,it.isFavourite)
            }
        }
    }

    suspend fun updateLocaleDatabase() {
        val gyms = apiServec.getGyms()

        val favouriteGymsList = gymDAO.getFavouertGym()
        gymDAO.addAll(gyms.map {
            LocalGym(it.id,it.name,it.places,it.isOpend )
        })
        gymDAO.updateAll(
            favouriteGymsList.map {
                GymFavouretState(id = it.id, true)
            }
        )
    }

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO)
        {
            gymDAO.update(GymFavouretState(id = gymId, isFavourite = state))
            return@withContext gymDAO.getAll()
        }

}