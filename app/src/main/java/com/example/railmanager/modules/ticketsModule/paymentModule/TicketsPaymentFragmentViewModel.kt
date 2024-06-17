package com.example.railmanager.modules.ticketsModule.paymentModule

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel

class TicketsPaymentFragmentViewModel : ViewModel() {
    val ticketsMethods = TicketsMethods()
    val ticketsActivityViewModel : TicketsActivityViewModel = TicketsActivityViewModel

    fun buyTicket(context : Context, ticket : Tickets ){
        ticketsMethods.buyTickets(context, ticket){
            ticketReturned ->
            if (ticketReturned != null) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "L'acquisto è andato a buon fine")
            }
            else{
                UsefulStaticMethods.showSimpleAlertDialog(context, "L'acquisto non è andato a buon fine")
            }

        }
    }
}