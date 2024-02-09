package com.example.firstprojectcompose

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.firstprojectcompose.data.CustomeWorkeManager
import com.example.firstprojectcompose.data.remot.GymsApiServec
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GymApplication :Application() , Configuration.Provider
{
    @Inject
    lateinit var customWorkerFactory : customWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(customWorkerFactory)
            .build()


}

class customWorkerFactory @Inject constructor(private val api : GymsApiServec):WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =CustomeWorkeManager(api ,appContext, workerParameters)
}
