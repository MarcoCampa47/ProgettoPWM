package com.example.railmanager.modules.dbModule.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state")
data class State (
    @PrimaryKey(autoGenerate = true) val idState : Int = 0,
    @ColumnInfo(name = "description") val description : String
)