package com.example.firstprojectcompose.persentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstprojectcompose.persentation.gymList.DefaultIcone
import com.example.firstprojectcompose.persentation.gymList.GymDetails


@Composable
fun GymDeatailsScreen() {
    val vm: GymDetailsViewModel = hiltViewModel()
    val item = vm.stat.value
    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {

            DefaultIcone(
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp),
                icone = Icons.Filled.Place,
                contentDiscrepation = "Location icone"
            )
            GymDetails(
                gym = it,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text(
                text = if (it.isOpend) "Gym is opne" else "Gym is Close",
                color = if(it.isOpend) Color.Green else Color.Red

                )
        }


    }


}