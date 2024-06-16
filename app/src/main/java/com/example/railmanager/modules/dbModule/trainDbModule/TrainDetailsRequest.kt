package com.example.railmanager.modules.dbModule.trainDbModule

data class TrainDetailsRequest (
    val idtrain : Int,
    val train_name : String,
    val available_seats : Int,
    val train_type : String,
    val departure_station : String,
    val departure_city : String,
    val arrival_station : String,
    val arrival_city : String

)