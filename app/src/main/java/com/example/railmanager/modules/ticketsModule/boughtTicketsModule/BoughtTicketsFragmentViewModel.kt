package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel

class BoughtTicketsFragmentViewModel : ViewModel() {

    val ticketsMethods = TicketsMethods()

    val _boughtTickets = MutableLiveData<List<Tickets>>()
    val boughtTickets: LiveData<List<Tickets>>
        get() = _boughtTickets

    fun getBoughtTickets(context : Context, user_id : Int) {
        ticketsMethods.getBoughtTicketsFromUser(context, user_id){
            boughtTickets ->
            if(boughtTickets != null)
                Log.d("BoughtTicketsFragmentViewModel", "getBoughtTickets: $boughtTickets")

            else
                UsefulStaticMethods.showSimpleAlertDialog(context, "Qualcosa è andato storto")

        }
    }

}