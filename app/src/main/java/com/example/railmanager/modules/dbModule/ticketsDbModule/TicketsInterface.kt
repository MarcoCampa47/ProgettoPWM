package com.example.railmanager.modules.dbModule.ticketsDbModule

import com.example.railmanager.modules.dbModule.searchDbModule.SearchRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketsInterface {

    @POST("railmanager/tickets/{idticket}")
    fun getTicketsBoughtFromUser(@Path("idticket") idticket: Int) : Call<Tickets>

    @POST("railmanager/tickets/search")
    fun searchTickets(@Body searchRequest: SearchRequest) : Call<List<Tickets>>

    @POST("railmanager/tickets/buy")
    fun buyTickets(@Body buyRequest: Tickets) : Call<Tickets>

}