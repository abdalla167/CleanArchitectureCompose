package com.example.firstprojectcompose.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.firstprojectcompose.data.remot.GymsApiServec
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CustomeWorkeManager @AssistedInject constructor (
      private val demoApi : GymsApiServec,
     @Assisted context: Context,
     @Assisted params: WorkerParameters

    ): CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {

        try {

           val response= demoApi.getGyms()
            if (response !=null){
                Log.d("TAG", "doWork: "+response.get(0).name)
                return Result.success()

            }
            else {
                Log.d("TAG", "doWork: Retry.....")

                return Result.retry()
            }
        }catch (e:Exception){

            Log.d("TAG", "doWork: failure")

            return Result.failure(Data.Builder().putString("error", e.toString()).build())
        }



    }
}