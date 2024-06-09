package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/*
Spiegazione sintassi Foreign jey
entity = Station::class: Specifica l'entità a cui questa chiave esterna fa riferimento.
parentColumns = ["idStation"]: Indica la colonna nella tabella Station che è la chiave primaria.
childColumns = ["station_id"]: Indica la colonna nella tabella Train che è la chiave esterna.
onDelete = ForeignKey.CASCADE: Specifica cosa fare quando la riga referenziata nella tabella Station viene eliminata. CASCADE significa che le righe corrispondenti nella tabella Train verranno eliminate automaticamente.
indices = [Index(value = ["station_id"])]: Aggiunge un indice sulla colonna station_id per migliorare le prestazioni delle query che utilizzano questa colonna.*/

@Entity(tableName = "train",
    foreignKeys = [
        ForeignKey(
            entity = State::class,
            parentColumns = ["idState"],
            childColumns = ["state_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Station::class,
            parentColumns = ["idStation"],
            childColumns = ["departure_station_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Station::class,
            parentColumns = ["idStation"],
            childColumns = ["arrival_station_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["state_id"]),
        Index(value = ["departure_station_id"])
    ]
)
data class Train (
    @PrimaryKey(autoGenerate = true) val idTrain : Long = 0,
    @ColumnInfo(name = "train_name", defaultValue = "undefinedName") val trainName: String,
    @ColumnInfo(name = "available_seats", defaultValue = "0") val availableSeats : Int,
    @ColumnInfo(name = "train_type", defaultValue = "Regionale") val trainType : String,
    @ColumnInfo(name = "state_id", defaultValue = "UndefinedState") val stateId: Int,
    @ColumnInfo(name = "departure_station_id") val departureStationID : Int,
    @ColumnInfo(name = "arrival_station_id") val arrivalStationID : Int
    )