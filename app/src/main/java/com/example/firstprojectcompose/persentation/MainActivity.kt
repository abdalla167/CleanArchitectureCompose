package com.example.firstprojectcompose.persentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.firstprojectcompose.persentation.details.GymDeatailsScreen
import com.example.firstprojectcompose.persentation.gymList.GymScreen
import com.example.firstprojectcompose.persentation.gymList.GymsViewModel
import com.example.firstprojectcompose.ui.theme.FirstProjectComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstProjectComposeTheme {
                GymAroundApp()
            }
        }
    }
    //root for all apllication
    // 1 - nav controllaer
    // 2 - nav host
    // 3 - start distnation
    @Composable
    private fun GymAroundApp() {
        // value not remove with confegration change
        val navController = rememberNavController()
        NavHost(navController, "gyms")
        {
            composable(route = "gyms")
            {
                val viewModel: GymsViewModel = hiltViewModel()
                GymScreen(
                    state = viewModel.stat.value,
                    onItemClick = { id -> navController.navigate("gyms/$id") },
                    onFavouriteClick = { id, oldeValue -> viewModel.toggleFavouriteStat(id, oldeValue) }
                )
            }
            composable(
                route = "gyms/{gym_id}",
                arguments = listOf(navArgument("gym_id")
                { type = NavType.IntType }),
                deepLinks = listOf(navDeepLink { uriPattern = "https://www.gymsaround.com/details/{gym_id}" })
            )
            {

                GymDeatailsScreen()
            }

        }

    }
}

