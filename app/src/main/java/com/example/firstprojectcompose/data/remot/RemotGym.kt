package com.example.firstprojectcompose.data.remot


import com.google.gson.annotations.SerializedName



data class RemotGym(

    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen: Boolean,

)