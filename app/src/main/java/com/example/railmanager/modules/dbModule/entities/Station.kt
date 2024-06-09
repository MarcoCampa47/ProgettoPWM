package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "station",
        foreignKeys = [
        ForeignKey(
        entity = City::class,
        parentColumns = ["idCity"],
        childColumns = ["city_ID"],
        onDelete = ForeignKey.CASCADE
    )
],
    indices = [Index(value = ["station_id"])])
data class Station (
    @PrimaryKey(autoGenerate = true) val idStation : Int = 0,
    @ColumnInfo(name = "station_name") val stationName : String,
    @ColumnInfo(name = "city_id") val cityID : Int
)