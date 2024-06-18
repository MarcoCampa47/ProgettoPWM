package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.boughtDbModule.BoughtTicketsRequest
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel

class BoughtTicketsFragmentViewModel : ViewModel() {

    val ticketsMethods = TicketsMethods()


    fun getBoughtTickets(context: Context, userId: Int, callback: (List<BoughtTicketsRequest>) -> Unit) {

        ticketsMethods.getBoughtTicketsFromUser(context, userId) { boughtTickets ->
            if (boughtTickets != null) {
                callback(boughtTickets)
            } else {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Qualcosa è andato storto")
            }
        }


    }

}
