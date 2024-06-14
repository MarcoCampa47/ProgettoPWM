package com.example.railmanager.modules.dbModule.cityDbModule

import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CityInterface {

    @GET("railmanager/allCities")
    fun getAllCities() : Call<List<City>>
}