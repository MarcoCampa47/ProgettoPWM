package com.example.railmanager.modules.dbModule.ticketsDbModule

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Tickets (

    val idticket: Int,
    val class_number: Int,
    val departure_time: String = "",
    val arrival_time: String ="",
    val adults_price: Double,
    val minors_price: Double,
    val data_purchase: String = "",
    val adults_number: Short,
    val minor_number : Short,
    val user_id : Long,
    val train_id : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt().toShort(),
        parcel.readInt().toShort(),
        parcel.readLong(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idticket)
        parcel.writeInt(class_number)
        parcel.writeString(departure_time)
        parcel.writeString(arrival_time)
        parcel.writeDouble(adults_price)
        parcel.writeDouble(minors_price)
        parcel.writeString(data_purchase)
        parcel.writeInt(adults_number.toInt())
        parcel.writeInt(minor_number.toInt())
        parcel.writeLong(user_id)
        parcel.writeInt(train_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tickets> {
        override fun createFromParcel(parcel: Parcel): Tickets {
            return Tickets(parcel)
        }

        override fun newArray(size: Int): Array<Tickets?> {
            return arrayOfNulls(size)
        }
    }
}

fun Tickets.withDefaultValues(): Tickets {
    return this.copy(
        departure_time = this.departure_time ?: "",
        arrival_time = this.arrival_time ?: "",
        data_purchase = this.data_purchase ?: ""
    )
}

fun List<Tickets>.withDefaultValues(): List<Tickets> {
    return this.map { it.withDefaultValues() }
}