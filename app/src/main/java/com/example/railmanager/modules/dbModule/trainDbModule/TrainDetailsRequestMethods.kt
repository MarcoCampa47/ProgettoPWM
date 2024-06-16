package com.example.railmanager.modules.dbModule.trainDbModule

import android.util.Log
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrainDetailsRequestMethods {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trainDetailsRequestApiService = retrofit.create(TrainDetailsRequestInterface::class.java)

    fun getTrainDetails(trainNumber: Int, callback: (TrainDetailsRequest?) -> Unit){
        val call = trainDetailsRequestApiService.getTrainDetails(trainNumber)

        call.enqueue(object : Callback<TrainDetailsRequest> {
            override fun onResponse(call: Call<TrainDetailsRequest>, response: Response<TrainDetailsRequest>) {
                if(response.isSuccessful){
                    val trainDetailsRequest = response.body()
                    if(trainDetailsRequest != null){
                        callback(trainDetailsRequest)
                    }
                    else{
                        Log.d("TrainDetailsRequestMethods", "Response body is null")
                        callback(null)
                    }
                }
                else{
                    Log.d("TrainDetailsRequestMethods", "Response not successful")
                }
            }

            override fun onFailure(call: Call<TrainDetailsRequest>, t: Throwable) {
                Log.d("TrainDetailsRequestMethods", "Request failed with exception: ${t.message}")
            }

        })
    }


}