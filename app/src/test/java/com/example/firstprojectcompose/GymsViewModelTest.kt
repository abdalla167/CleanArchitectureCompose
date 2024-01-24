package com.example.firstprojectcompose

import com.example.firstprojectcompose.data.DummyGymsList
import com.example.firstprojectcompose.data.GymsReposatery
import com.example.firstprojectcompose.data.local.GymDAO
import com.example.firstprojectcompose.data.local.GymFavouretState
import com.example.firstprojectcompose.data.local.LocalGym
import com.example.firstprojectcompose.data.remot.GymsApiServec
import com.example.firstprojectcompose.data.remot.RemotGym
import com.example.firstprojectcompose.domain.GetInitialGymsAllUseCAse
import com.example.firstprojectcompose.domain.GetSortedUsecase
import com.example.firstprojectcompose.domain.ToggelFavouretStatUseCase
import com.example.firstprojectcompose.persentation.gymList.GymsScreenState
import com.example.firstprojectcompose.persentation.gymList.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    @Test
    fun loadingStat_isLoading_true()=scope.runTest {
        // Given
        val viewModel = getGymsViewModel()
        val state = viewModel.stat.value
        assert(state == GymsScreenState(
            gym = emptyList(),
            isLoading = true,
            error = null
        ))
    }

    @Test
    fun loadingContentStat_true()=scope.runTest {
        // Given
        val viewModel = getGymsViewModel()
        advanceUntilIdle()
        val state = viewModel.stat.value
        assert(
            state == GymsScreenState(
            gym = DummyGymsList.getDummyGymsList(),
            isLoading = false,
            error = null
        ))
    }


    private fun getGymsViewModel(): GymsViewModel {

        val gymsReposatery =GymsReposatery(TestGymsApiService(),TestGymsDAO(),dispatcher)
        val getSortedUsecase =GetSortedUsecase(gymsReposatery)
        val getInitialGymsAllUseCAse = GetInitialGymsAllUseCAse(gymsReposatery,getSortedUsecase)
        val toggelfavouretstatusecaseFactory = ToggelFavouretStatUseCase(gymsReposatery,getInitialGymsAllUseCAse)
        return GymsViewModel(getInitialGymsAllUseCAse,toggelfavouretstatusecaseFactory,dispatcher )
    }
    class TestGymsApiService : GymsApiServec
    {
        override suspend fun getGyms(): List<RemotGym> {
         return DummyGymsList_.getDummyGymsList()
        }

        override suspend fun getGym(id: Int): Map<String, RemotGym> {
            TODO("Not yet implemented")
        }

    }
    class TestGymsDAO : GymDAO{

        private var gyms =HashMap<Int , LocalGym>()

        override suspend fun getAll(): List<LocalGym> {
           return gyms.values.toList()
        }

        override suspend fun addAll(gyms: List<LocalGym>) {
           gyms.forEach {
               this.gyms[it.id] = it

           }
        }

        override suspend fun update(gymfavouertStat: GymFavouretState) {

            UpdatGym(gymfavouertStat)
        }

        override suspend fun getFavouertGym(): List<LocalGym> {

            return gyms.values.toList().filter { it.isFavourite }


        }

        override suspend fun updateAll(gymfavouertStat: List<GymFavouretState>) {
            gymfavouertStat.forEach {
                UpdatGym(it)
            }
        }

        private fun UpdatGym(gymfavouertStat: GymFavouretState) {
            val gym = this.gyms[gymfavouertStat.id]
            gym?.let {
                this.gyms[it.id] = it.copy(isFavourite = gymfavouertStat.isFavourite)
            }
        }
    }


}