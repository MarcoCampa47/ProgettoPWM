package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.user_ticketDbModule.UserTicketRequest
import com.example.railmanager.modules.dbModule.user_ticketDbModule.UserTicketRequestMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

class BoughtTicketsDetailFragmentViewModel : ViewModel() {
    val userTicketRequestMethods = UserTicketRequestMethods()
    val ticketsActivityViewModel = TicketsActivityViewModel

    fun refundTickets(context : Context, fragment : Fragment, user_ticketIdToRemove : Long, date : String, ticketid : Int) {

        val actualDateFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val actualDate =  LocalDateTime.now().format(actualDateFormatter).toString().replace(" ", "T").replace("/","-")
        val data_purchase = UsefulStaticMethods.formattaDataLikeDb(date).replace(" ", "T").replace("/","-")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        if( ChronoUnit.HOURS.between(
            LocalDateTime.parse(data_purchase, formatter ),
            LocalDateTime.parse(actualDate, formatter) ) > 24
            ) {
                UsefulStaticMethods.showSimpleAlertDialog(context, "Attenzione! Il periodo di rimborso è entro 24 ore. Impossibile effettuare il rimborso")
                return
            }


        val user_ticketToRefund = UserTicketRequest(user_ticketIdToRemove, ticketsActivityViewModel.getIdUtente().toLong() , ticketid, 0, 0, "")

        userTicketRequestMethods.refundTickets(context, user_ticketToRefund){
            callback ->
            if(callback != null && callback.ticketid == -1){
                UsefulStaticMethods.showSimpleAlertDialog(context, "Verrà effettuato il rimborso")
                fragment.findNavController().navigate(BoughtTicketsDetailFragmentDirections.actionBoughtTicketsDetailFragmentToBoughtTicketsFragment())
            }
            else{
                UsefulStaticMethods.showSimpleAlertDialog(context, "Non è possibile effettuare il rimborso.")
            }
        }
    }

}