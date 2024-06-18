package com.example.railmanager.modules.dbModule.boughtDbModule

data class BoughtTicketsRequest (

    val iduser_ticket: Long,
    val train_name: String,
    val train_type: String,
    val idstate: Int,
    val state_description: String,
    val departure_city_name: String,
    val arrival_city_name: String,
    val departure_station_name: String,
    val arrival_station_name: String,
    val departure_station_id: Int,
    val arrival_station_id: Int,
    val date_purchase: String,
    val adults_number: Int,
    val minors_number: Int,
    val adults_price: Double,
    val minors_price: Double

)