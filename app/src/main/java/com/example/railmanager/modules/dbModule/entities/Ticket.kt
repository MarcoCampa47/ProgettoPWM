package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "ticket",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["idUser"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.SET_NULL
        )],
    indices = [Index(value = ["user_id"])]
)
data class Ticket (
    @PrimaryKey(autoGenerate = true) val idTicket : Long = 0,
    @ColumnInfo(name = "class_number", defaultValue = "2") val classNumber : Short,
    @ColumnInfo(name = "departure_time") val departureTime : Date,
    @ColumnInfo(name = "arrival_time") val arrivalTime : Date,
    @ColumnInfo(name = "price_for_adults") val priceAdults : Double,
    @ColumnInfo(name = "price_for_minors") val priceMinors : Double,
    @ColumnInfo(name = "num_adults_ticket") val numberAdultsTicket : Short?,
    @ColumnInfo(name = "num_minors_ticket") val numberMinorTicket : Short?,
    @ColumnInfo(name = "data_purchase") val dataPurchase : Date?,
    @ColumnInfo(name = "user_id") val userID : Long
)