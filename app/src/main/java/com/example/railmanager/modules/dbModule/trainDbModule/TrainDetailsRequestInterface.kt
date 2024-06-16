package com.example.railmanager.modules.dbModule.trainDbModule

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrainDetailsRequestInterface {
    @GET("railmanager/train/{id}")
    fun getTrainDetails(@Path("id") id: Int): Call<TrainDetailsRequest>

}