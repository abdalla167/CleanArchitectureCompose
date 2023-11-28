package com.example.firstprojectcompose.gyms.persentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.firstprojectcompose.R
import com.example.firstprojectcompose.gyms.persentation.details.GymDeatailsScreen
import com.example.firstprojectcompose.gyms.persentation.gymList.GymScreen
import com.example.firstprojectcompose.gyms.persentation.gymList.GymsViewModel
import com.example.firstprojectcompose.ui.theme.FirstProjectComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FirstProjectComposeTheme {
                // GymScreen()
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
                val viewModel:GymsViewModel = viewModel()
                GymScreen(state = viewModel.stat.value, onItemClick ={ id ->
                    navController.navigate("gyms/$id")
                },
                    onFavouriteClick = {id,oldeValue ->
                        viewModel.toggleFavouriteStat(id,oldeValue)
                    })
            }
            composable(
                route = "gyms/{gym_id}",
                arguments = listOf(navArgument("gym_id")
                {
                    type = NavType.IntType
                }),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "https://www.gymsaround.com/details/{gym_id}"
                    }
                )
            )
            {

                GymDeatailsScreen()
            }

        }

    }

    @Composable
    fun MyBoxLayout() {
        Row() {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Black)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(Color.Gray)
            ) {
                Text(text = "One ", Modifier.align(Alignment.TopStart), color = Color.White)
                Text(text = "Two ", Modifier.align(Alignment.Center), color = Color.White)
                Text(text = "Three ", Modifier.align(Alignment.BottomEnd), color = Color.White)

            }
            MyLayout()

        }
    }

    @Composable
    fun MyLayout() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            MyText()
            MyButton()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Logo ...")
                MyImage()
            }

        }
    }

    @Composable
    fun MyItem() {


    }

    @Composable
    fun MyText() {

        Text(
            text = "Any Thing In First",
            style = TextStyle(
                color = Color.Red,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,

            )

    }

    @Composable
    fun MyButton() {
        //must be obsarble
        var buttonIsEnabled by remember { mutableStateOf(true) }
        Button(
            onClick = { buttonIsEnabled = false },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                disabledContainerColor = Color.LightGray
            ),
            enabled = buttonIsEnabled
        )
        {

            Text(text = if (buttonIsEnabled) "Click Me" else " I'M Dissable ")
        }
    }


    @Composable
    fun MyTextFiled() {
        //i can save value ( remeber when i make recompsation )
        var emailAddress by remember { mutableStateOf("") }
        TextField(
            value = emailAddress,
            onValueChange = {
                emailAddress = it
            },
            label = { Text(text = "Email Address") }
        )
    }

    @Composable
    fun MyImage() {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "jetPack Compose Logo"
        )
    }


}

