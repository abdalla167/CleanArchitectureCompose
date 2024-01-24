package com.example.firstprojectcompose.persentation.gymList

import com.example.firstprojectcompose.domain.Gym

data  class GymsScreenState (
    val gym:List<Gym>,
    val isLoading : Boolean,
    val error :String ?=null
)