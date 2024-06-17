package com.example.railmanager.modules.dbModule.user_ticketDbModule

data class UserTicketRequest (
    val iduser_ticket : Long,
    val userid : Long,
    val ticketid : Int,
    val adults_number : Int,
    val minors_number : Int,
    val data_purchase : String
)