package com.example.railmanager.modules.dbModule.searchDbModule

data class SearchRequest (

    val startCityId: String,
    val startStationId: String,
    val endCityId: String,
    val endStationId: String,
    val departureDate : String,
    val arrivalDate : String,
    val adultNumber : String,
    val childNumber : String

)