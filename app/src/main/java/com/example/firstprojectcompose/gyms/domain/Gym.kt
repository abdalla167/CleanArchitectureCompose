package com.example.firstprojectcompose.gyms.domain


data class Gym(

    val id: Int,

    val name: String,

    val places: String,

    val isOpend: Boolean,

    val isFavourite: Boolean = false
)