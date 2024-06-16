package com.example.railmanager.modules.dbModule.ticketsDbModule

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.searchDbModule.SearchRequest
import com.example.railmanager.modules.ticketsModule.searchModule.TrainRoutesFragment
import com.example.railmanager.modules.ticketsModule.searchModule.TrainRoutesFragmentDirections
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class TicketsMethods {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val ticketsServiceApi = retrofit.create(TicketsInterface::class.java)

    fun runSearch(fragment: Fragment, context: Context, searchRequest: SearchRequest){

        getTicketsFromResearch(context, searchRequest) { ticketsList ->
            // Qui puoi gestire la lista di biglietti ricevuta
            if (ticketsList != null) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Biglietti trovati")

                //Sostituisco eventuali valori a null con i valori di default, in quanto ticket è serializable e non può avere valori null
                val ticketsListWithDefault = ticketsList.withDefaultValues()

                // Naviga utilizzando Safe Args
                fragment.findNavController().navigate(TrainRoutesFragmentDirections.actionTrainRoutesFragmentToTicketsPaymentFragment(ticketsListWithDefault.toTypedArray()))
            } else {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Nessun biglietto trovato con questi criteri")
            }
        }
    }

    fun getTicketsFromResearch(context: Context, searchRequest: SearchRequest, callback: (List<Tickets>?) -> Unit) {

        val json = Gson().toJson(searchRequest)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

        val call = ticketsServiceApi.searchTickets(searchRequest)

        call.enqueue(object : Callback<List<Tickets>> {
            override fun onResponse(call: Call<List<Tickets>>, response: Response<List<Tickets>>) {
                if (response.isSuccessful) {
                    val ticketsListResponse = response.body()
                    if (ticketsListResponse != null) {
                        //Log.d("Risposta dal server", ticketsListResponse.toString())
                        if (ticketsListResponse.isEmpty()) {
                            UsefulStaticMethods.showSimpleAlertDialog(context, "Nessun biglietto trovato")
                        } else {
                            callback(ticketsListResponse)
                        }
                    } else {
                        UsefulStaticMethods.showSimpleAlertDialog(context, "Risposta vuota dal server")
                    }
                } else {
                    UsefulStaticMethods.showSimpleAlertDialog(context, "Impossibile ricevere una risposta dal server")
                }
            }

            override fun onFailure(call: Call<List<Tickets>>, t: Throwable) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Comunicazione col server fallita: ${t.message}")
            }
        })
    }

}