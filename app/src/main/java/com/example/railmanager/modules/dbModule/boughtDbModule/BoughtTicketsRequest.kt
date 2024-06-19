package com.example.railmanager.modules.dbModule.boughtDbModule

import android.os.Parcelable

import android.os.Parcel

data class BoughtTicketsRequest(
    val iduser_ticket: Long,
    val ticketid : Long,
    val train_name: String,
    val train_type: String,
    val idstate: Int,
    val state_description: String,
    val class_number: Short,
    val departure_time: String,
    val arrival_time: String,
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt().toShort(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(iduser_ticket)
        parcel.writeString(train_name)
        parcel.writeString(train_type)
        parcel.writeInt(idstate)
        parcel.writeString(state_description)
        parcel.writeInt(class_number.toInt())
        parcel.writeString(departure_time)
        parcel.writeString(arrival_time)
        parcel.writeString(departure_city_name)
        parcel.writeString(arrival_city_name)
        parcel.writeString(departure_station_name)
        parcel.writeString(arrival_station_name)
        parcel.writeInt(departure_station_id)
        parcel.writeInt(arrival_station_id)
        parcel.writeString(date_purchase)
        parcel.writeInt(adults_number)
        parcel.writeInt(minors_number)
        parcel.writeDouble(adults_price)
        parcel.writeDouble(minors_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoughtTicketsRequest> {
        override fun createFromParcel(parcel: Parcel): BoughtTicketsRequest {
            return BoughtTicketsRequest(parcel)
        }

        override fun newArray(size: Int): Array<BoughtTicketsRequest?> {
            return arrayOfNulls(size)
        }
    }
}
