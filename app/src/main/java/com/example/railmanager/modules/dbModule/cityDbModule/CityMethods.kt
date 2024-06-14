package com.example.railmanager.modules.dbModule.cityDbModule

import android.content.Context
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.userDbModule.UserInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityMethods {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cityApiService = retrofit.create(CityInterface::class.java)

    fun getAllCities(context: Context, callback: (HashMap<Int, String>) -> Unit) : HashMap<Int, String> {
        val citiesMap: HashMap<Int, String> = HashMap()
        val call = cityApiService.getAllCities()

        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.isSuccessful) {
                    val cities = response.body()
                    if (cities != null) {
                        for (city in cities) {
                            citiesMap[city.idcity] = city.city_name
                        }
                        callback(citiesMap)
                    } else {
                        UsefulStaticMethods.showSimpleAlertDialog(context, "Si è verificato un errore nel caricamento delle città")
                        callback(citiesMap) // Ritornare comunque l'oggetto vuoto in caso di errore
                    }
                } else {
                    UsefulStaticMethods.showSimpleAlertDialog(context, "Errore di comunicazione con il server. Riprova")
                    callback(citiesMap) // Ritornare comunque l'oggetto vuoto in caso di errore
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Errore di comunicazione con il server. Codice: ${t.message}")
                callback(citiesMap) // Ritornare comunque l'oggetto vuoto in caso di errore
            }
        })
        return citiesMap
    }

}