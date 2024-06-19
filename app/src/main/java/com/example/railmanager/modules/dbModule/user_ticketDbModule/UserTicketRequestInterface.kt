package com.example.railmanager.modules.dbModule.user_ticketDbModule

import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserTicketRequestInterface {
    @POST("railmanager/tickets/buy")
    fun buyTickets(@Body buyRequest: UserTicketRequest) : Call<UserTicketRequest>

    @POST("railmanager/tickets/refund")
    fun refundTickets(@Body refundRequest: UserTicketRequest) : Call<UserTicketRequest>
}