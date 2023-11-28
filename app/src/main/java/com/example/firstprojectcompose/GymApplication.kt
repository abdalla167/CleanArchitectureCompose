package com.example.firstprojectcompose

import android.app.Application
import android.content.Context

class GymApplication :Application()
{

    init {

        application =this
    }

    companion object
    {

        private lateinit var application: GymApplication
        fun getApplicationContext():Context = application.applicationContext
    }

}