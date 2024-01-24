package com.example.firstprojectcompose.persentation.gymList

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstprojectcompose.data.di.MainDispatcher
import com.example.firstprojectcompose.domain.GetInitialGymsAllUseCAse
import com.example.firstprojectcompose.domain.ToggelFavouretStatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  GymsViewModel @Inject constructor(
    private val getInitialAllGymsUseCase: GetInitialGymsAllUseCAse,
    private val toggleFavouriteStateUseCase: ToggelFavouretStatUseCase,
    @MainDispatcher  private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _stat by mutableStateOf(
        GymsScreenState(
            gym = emptyList(),
            isLoading = true
        )
    )
    val stat: State<GymsScreenState>
        get() = derivedStateOf { _stat }


    private val errorHandle = CoroutineExceptionHandler { _, throwable ->

        throwable.printStackTrace()
        _stat = _stat.copy(
            isLoading = false,
            error = throwable.message
        )

    }

    init {
        getGyms()
    }

    fun getGyms() {
        viewModelScope.launch(errorHandle+ dispatcher)
        {
            val receivAllGyms = getInitialAllGymsUseCase.Invoke()
            _stat = _stat.copy(
                gym = receivAllGyms,
                isLoading = false
            )
        }
    }

    fun toggleFavouriteStat(gymId: Int, oldValue: Boolean) {

        viewModelScope.launch(errorHandle+ dispatcher)
        {
            val updateListGyms = toggleFavouriteStateUseCase.invoke(gymId, oldValue)
            _stat = _stat.copy(updateListGyms)
        }
    }


}