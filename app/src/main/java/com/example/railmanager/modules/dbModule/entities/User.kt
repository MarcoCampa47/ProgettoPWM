package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) val idUser : Long = 0,
    @ColumnInfo(name = "e-mail") val email : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "surname") val surname : String,
    @ColumnInfo(name = "birthdate") val birthdate : Date,
)