package com.example.firstprojectcompose.gyms.persentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstprojectcompose.gyms.data.remot.GymsApiServec
import com.example.firstprojectcompose.gyms.domain.Gym
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymDetailsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    val stat = mutableStateOf<Gym?>(null)
    private var apiServec: GymsApiServec
    private val errorHandle = CoroutineExceptionHandler { _, throwable ->

        throwable.printStackTrace()

    }

    init {


        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cairo-gyms-822da-default-rtdb.firebaseio.com/").build()


        apiServec = retrofit.create(GymsApiServec::class.java)
        val gym_id = stateHandle.get<Int>("gym_id") ?: 0
        getGym(gym_id)

    }

    private fun getGym(id: Int) {

        viewModelScope.launch {

            val gym = withContext(Dispatchers.IO) {
                apiServec.getSingelGym(id).values.first().let {
                    Gym(
                        it.id,
                        it.name, it.places,it.isOpend
                    )

                }
            }

        }

    }


}