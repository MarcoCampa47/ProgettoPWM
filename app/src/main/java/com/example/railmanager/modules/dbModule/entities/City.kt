package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = true) val idCity : Int = 0,
    @ColumnInfo(name = "city_name", defaultValue = "UndefinedCity") val cityName : String
)