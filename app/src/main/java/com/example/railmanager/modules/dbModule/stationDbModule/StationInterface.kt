package com.example.railmanager.modules.dbModule.stationDbModule

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StationInterface {
    @GET("railmanager/stations/fromCity{id}")
    fun getStationsFromCityId(@Path("id") idCity: Int): Call<List<Station>>

}