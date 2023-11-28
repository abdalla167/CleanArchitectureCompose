package com.example.firstprojectcompose.gyms.persentation.gymList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstprojectcompose.gyms.domain.Gym
import com.example.firstprojectcompose.ui.theme.Purple80

@Composable
fun GymScreen(
    state:GymsScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteClick: (id:Int,oldvalue:Boolean) -> Unit

) {


    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxHeight()
    ){
        LazyColumn()
        {
            items(state.gym)
            { gym ->
                GymItem(
                    gym = gym,
                    onFavouriteClick = {id,oldalue ->
                        onFavouriteClick(id,oldalue)
                                       },
                    onItemClick = {id->
                        onItemClick(id)
                    }
                )
            }
        }
        if (state.isLoading) CircularProgressIndicator()
       state.error?.let {
           Text(it)
       }

    }


}

@Composable
fun GymItem(gym: Gym, onFavouriteClick: (Int,Boolean) -> Unit, onItemClick: (Int) -> Unit) {
    val icone = if (gym.isFavourite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder

    }
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(gym.id) })
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            DefaultIcone(Modifier.weight(0.15f), Icons.Filled.Place, "Location Icone ")
            GymDetails(gym, Modifier.weight(0.70f))
            DefaultIcone(Modifier.weight(0.15f), icone, "Favourite Gym Icon ") {
                onFavouriteClick(gym.id,gym.isFavourite)
            }
        }
    }
}

@Composable
fun DefaultIcone(
    modifier: Modifier,
    icone: ImageVector,
    contentDiscrepation: String,
    onClick: () -> Unit = {},
) {

    // 1 - inatioal value
    // 2- rabed in mutable state
    // 3 - saved this value throw out recomposation
    // 4 - change the value when the user make action or click or event
    Image(
        imageVector = icone,
        contentDescription = "Favourt Gym Icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        colorFilter = ColorFilter.tint(Color.Gray)
    )


}

@Composable
fun GymDetails(
    gym: Gym,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.labelLarge,
            color = Purple80
        )
        Text(
            text = gym.places,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun _GymScreenPrview() {
//    FirstProjectComposeTheme {
//        GymScreen()
//    }
//
//}


