package com.example.railmanager.modules.ticketsModule.paymentModule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import com.example.railmanager.modules.dbModule.user_ticketDbModule.UserTicketRequest
import com.example.railmanager.modules.dbModule.user_ticketDbModule.UserTicketRequestMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel

class TicketsPaymentFragmentViewModel : ViewModel() {
    val userTicketRequestMethods = UserTicketRequestMethods()

    fun buyTicket(context : Context, userTicketRequest : UserTicketRequest, fragment: TicketsPaymentFragment){
        userTicketRequestMethods.buyTickets(context, userTicketRequest){
            ticketReturned ->
            if (ticketReturned != null) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "L'acquisto è andato a buon fine")
                fragment.findNavController().navigate(TicketsPaymentFragmentDirections.actionTicketsPaymentFragmentToBoughtTicketsFragment())
            }
            else{
                UsefulStaticMethods.showSimpleAlertDialog(context, "L'acquisto non è andato a buon fine")
            }

        }
    }
}