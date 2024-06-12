package com.example.railmanager.modules.dbModule.userDbModule

data class User (
    val iduser : Int,
    val email: String,
    val name : String,
    val surname : String,
    val password : String,
    val date: String,
    val phoneNumber: String
)
