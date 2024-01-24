package com.example.firstprojectcompose.persentation.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstprojectcompose.data.remot.GymsApiServec
import com.example.firstprojectcompose.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class GymDetailsViewModel @Inject constructor (private var stateHandle: SavedStateHandle, var apiServec: GymsApiServec) : ViewModel() {

    val stat = mutableStateOf<Gym?>(null)
    private val errorHandle = CoroutineExceptionHandler { _, throwable ->

        throwable.printStackTrace()

    }

    init {

        val gym_id = stateHandle.get<Int>("gym_id") ?: 0
        getGym(gym_id)

    }

    private fun getGym(id: Int) {

        viewModelScope.launch {


            val gym =getGymFromRemotDB(id)
            Log.d("TAG", "getGym: ${gym.name}")
            stat.value=gym

        }
    }
    suspend fun getGymFromRemotDB(id:Int)=

        withContext(errorHandle+Dispatchers.IO) {
            apiServec.getGym(id).values.first().let {

                Gym(
                    it.id,
                    it.name,
                    it.place,
                    it.isOpen
                )

            }
        }



}