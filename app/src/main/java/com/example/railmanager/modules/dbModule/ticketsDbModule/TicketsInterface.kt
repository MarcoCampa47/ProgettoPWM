package com.example.railmanager.modules.dbModule.ticketsDbModule

import retrofit2.http.POST
import retrofit2.http.Path

interface TicketsInterface {

    @POST("/tickets/{idticket}")
    fun getTicketsBoughtFromUser(@Path("idticket") idticket: Int) : Class<Tickets>

}