package com.example.railmanager.modules.dbModule.user_ticketDbModule

import android.content.Context
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserTicketRequestMethods {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userTicketRequestApiService = retrofit.create(UserTicketRequestInterface::class.java)

    fun buyTickets(context: Context, ticketToBuy: UserTicketRequest, callback: (UserTicketRequest?) -> Unit){
        val call = userTicketRequestApiService.buyTickets(ticketToBuy)
        call.enqueue(object : Callback<UserTicketRequest> {
            override fun onResponse(call: Call<UserTicketRequest>, response: Response<UserTicketRequest>) {
                if(response.isSuccessful){
                    val boughtTicket = response.body()
                    if(boughtTicket != null && boughtTicket.iduser_ticket.toInt() != -1){
                        callback(boughtTicket)
                    }
                    else{
                        callback(null)
                    }
                }
                else{
                    UsefulStaticMethods.showSimpleAlertDialog(context, "Impossibile ricevere una risposta dal server")
                }
            }

            override fun onFailure(call: Call<UserTicketRequest>, t: Throwable) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Comunicazione col server fallita: ${t.message}")
            }
        })
    }
}