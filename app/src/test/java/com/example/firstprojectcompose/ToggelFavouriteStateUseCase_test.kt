package com.example.firstprojectcompose

import com.example.firstprojectcompose.data.DummyGymsList
import com.example.firstprojectcompose.data.GymsReposatery
import com.example.firstprojectcompose.domain.GetInitialGymsAllUseCAse
import com.example.firstprojectcompose.domain.GetSortedUsecase
import com.example.firstprojectcompose.domain.ToggelFavouretStatUseCase
import com.example.firstprojectcompose.persentation.gymList.GymsScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
class ToggelFavouriteStateUseCase_test
{
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    @Test
    fun toggelFavouriteState_Update ()=scope.runTest {

        val gymsRepository = GymsReposatery(TestGymsApiService(), TestGymsDAO(),dispatcher)
        val getSortedUsecase = GetSortedUsecase(gymsRepository)
        val useCase = ToggelFavouretStatUseCase(gymsRepository,getSortedUsecase)

        gymsRepository.loadGyms()
        advanceUntilIdle()
        val gyms = DummyGymsList.getDummyGymsList()
        val gymsUnderTest= gyms[0]
        val isFavourite = gymsUnderTest.isFavourite
        val UpdateList = useCase.invoke(gymsUnderTest.id,isFavourite)
        advanceUntilIdle()

        assert(UpdateList[0].isFavourite == !isFavourite)


    }


}