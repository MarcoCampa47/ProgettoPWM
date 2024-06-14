package com.example.railmanager.modules.dbModule.cityDbModule

import android.content.Context
import android.util.Log
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.stationDbModule.Station
import com.example.railmanager.modules.dbModule.stationDbModule.StationInterface
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
    val stationApiService = retrofit.create(StationInterface::class.java)



    //Rendo i nomi della citta / citta+stazione la chiave della mappa, così da avere a valore la coppia di id città/stazione, se la stazione non esiste
    //la coppia avrà valore (n,-1)
    /*In sintesi:
    * Il metodo restituisce una mappa contenente <Nome Città, Coppia <idCitta, -1>>, nel caso in cui non siano presenti stazioni per la città o non si vogliano indicare
    * Oppure contiene <Nome Città + Nome stazione, Coppia <idCitta, idStazione>> */
    fun getAllCities(context: Context, callback: (HashMap<String, Pair<Int, Int>>) -> Unit) {
        val citiesMap: HashMap<String, Pair<Int, Int>> = HashMap()
        val call = cityApiService.getAllCities()

        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.isSuccessful) {
                    val cities = response.body()
                    if (cities != null) {
                        var count = 0
                        for (city in cities) {
                            getAllStationFromCityID(context, city.idcity) { stationList ->
                                // Se la città ha stazioni, le elenco
                                if (stationList.isNotEmpty()) {
                                    for (station in stationList) {
                                        citiesMap[city.city_name] = Pair(city.idcity, -1)
                                        val id_station = station.last().digitToInt()
                                        val station_name = station.dropLast(1)
                                        citiesMap["${city.city_name}, $station_name"] = Pair(city.idcity, id_station)
                                        Log.d("DEBUG", "Station Name: $station")
                                    }
                                } else {
                                    citiesMap[city.city_name] = Pair(city.idcity, -1)
                                }
                                count++
                                // Chiamare il callback solo quando tutte le città sono state processate
                                if (count == cities.size) {
                                    callback(citiesMap)
                                }
                            }
                        }
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
    }

    fun getAllStationFromCityID(context: Context, cityID: Int, callback: (List<String>) -> Unit) {
        val call = stationApiService.getStationsFromCityId(cityID)

        call.enqueue(object : Callback<List<Station>> {
            override fun onResponse(call: Call<List<Station>>, response: Response<List<Station>>) {
                if (response.isSuccessful) {
                    val stations = response.body()
                    if (stations != null) {
                        val stationList = stations.map { "${it.station_name}${it.idstation}" }
                        callback(stationList)
                    } else {
                        UsefulStaticMethods.showSimpleAlertDialog(context, "Si è verificato un errore nel caricamento delle stazioni")
                        callback(emptyList()) // Ritornare una lista vuota in caso di errore
                    }
                } else {
                    UsefulStaticMethods.showSimpleAlertDialog(context, "Errore di comunicazione con il server. Riprova")
                    callback(emptyList()) // Ritornare una lista vuota in caso di errore
                }
            }

            override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                Log.d("DEBUG", "Stazione non trovata per la città con id $cityID. Codice: ${t.message}")
                callback(emptyList()) // Ritornare una lista vuota in caso di errore
            }
        })
    }


}