package com.example.railmanager.modules.dbModule.ticketsDbModule

data class Tickets (

    val idticket: Int,
    val class_number: Int,
    val departure_time: String,
    val arrival_time: String,
    val adults_price: Double,
    val minors_price: Double,
    val data_purchase: String,
    val adults_number: Short,
    val minor_number : Short,
    val user_id : Long,
    val train_id : Int
)