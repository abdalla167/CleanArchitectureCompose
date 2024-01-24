package com.example.firstprojectcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.firstprojectcompose.data.DummyGymsList.getDummyGymsList
import com.example.firstprojectcompose.persentation.SemanticsDescription
import com.example.firstprojectcompose.persentation.gymList.GymScreen
import com.example.firstprojectcompose.persentation.gymList.GymsScreenState
import com.example.firstprojectcompose.ui.theme.FirstProjectComposeTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {

    //ui test
    //
    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {

            FirstProjectComposeTheme {

                GymScreen(state = GymsScreenState(gym = emptyList(), true),
                    onItemClick = {},
                    onFavouriteClick = { _: Int, _: Boolean -> })

            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun LoadContent_State() {

        val gymList = getDummyGymsList()

        testRule.setContent {
            FirstProjectComposeTheme {
                GymScreen(
                    state = GymsScreenState(gym = gymList, isLoading = false),
                    onItemClick = {},
                    onFavouriteClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onAllNodesWithText(gymList[0].name,false,false)[0].assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertDoesNotExist()

    }

    @Test
    fun errorState_isActive() {

        val errorText = "Faild to load data"
        testRule.setContent {
            FirstProjectComposeTheme {
                GymScreen(
                    state = GymsScreenState(
                        gym = emptyList(),
                        isLoading = false,
                        error = errorText
                    ), onItemClick = {}, onFavouriteClick = { _: Int, _: Boolean -> })
            }


        }
        testRule.onNodeWithText(errorText).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING)
            .assertDoesNotExist()


    }

    @Test
    fun onItemClick_idPassedCorrectly()
    {
        val gymList = getDummyGymsList()

        val gymItem =gymList[0]
        testRule.setContent {
            FirstProjectComposeTheme {
                GymScreen(
                    state = GymsScreenState(gym = gymList, isLoading = false),
                    onItemClick = { id->
                            assert(id== gymItem.id)
                    },
                    onFavouriteClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onAllNodesWithText(gymItem.name)[0].performClick()

    }
    @Test
    fun onFavouriteClick_idPassedCorrectly(){
        val gymList = getDummyGymsList()
        val gymItem =gymList[0]
        testRule.setContent {
            FirstProjectComposeTheme {
                GymScreen(
                    state = GymsScreenState(gym = gymList, isLoading = false),
                    onItemClick = {},
                    onFavouriteClick = { id: Int, _: Boolean ->
                        assert(id== gymItem.id)
                    },
                )
            }

        }
        testRule.onAllNodesWithText(gymItem.name)[0].performClick()
    }
}