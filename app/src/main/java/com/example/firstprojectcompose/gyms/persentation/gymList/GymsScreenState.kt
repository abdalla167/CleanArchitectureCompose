package com.example.firstprojectcompose.gyms.persentation.gymList

import com.example.firstprojectcompose.gyms.domain.Gym

data  class GymsScreenState (
    val gym:List<Gym>,
    val isLoading : Boolean,
    val error :String ?=null
)