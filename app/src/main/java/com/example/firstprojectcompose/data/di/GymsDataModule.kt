package com.example.firstprojectcompose.data.di

import android.content.Context
import androidx.room.Room
import com.example.firstprojectcompose.data.local.GymDAO
import com.example.firstprojectcompose.data.local.GymDataBase
import com.example.firstprojectcompose.data.remot.GymsApiServec
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun provideRoomDAO(db: GymDataBase): GymDAO {

        return db.dao

    }

    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): GymDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            GymDataBase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit
    {
     return   Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cairo-gyms-822da-default-rtdb.firebaseio.com/")
            .build()

    }

    @Singleton
    @Provides
    fun provideApi(retrofit:Retrofit): GymsApiServec
    {
        return retrofit.create(GymsApiServec::class.java)

    }



}