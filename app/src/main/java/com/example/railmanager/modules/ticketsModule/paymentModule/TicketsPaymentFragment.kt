package com.example.railmanager.modules.ticketsModule.paymentModule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.trainDbModule.TrainDetailsRequest
import com.example.railmanager.modules.dbModule.user_ticketDbModule.UserTicketRequest
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel
import com.example.railmanager.modules.ticketsModule.resultModule.TicketsResultFragmentArgs
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TicketsPaymentFragment : Fragment() {

    private val args: TicketsPaymentFragmentArgs by navArgs()
    val ticketsPaymentFragmentViewModel = TicketsPaymentFragmentViewModel()
    val ticketsActivityViewModel = TicketsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_payment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticket = args.ticketToBuy

        // Recupera la stringa JSON
        val jsonTrainDetailsString = args.trainDetails

        // Converti da JSON a struttura dati complessa
        val gson = Gson()
        val trainDetails = gson.fromJson(jsonTrainDetailsString, TrainDetailsRequest::class.java)

        val destinationTextView : TextView = view.findViewById(R.id.destinationTextViewTicketPaymentFragment)
            destinationTextView.text = trainDetails.arrival_city + ", " + trainDetails.arrival_station
        val adultsNumberTextView: TextView = view.findViewById(R.id.adultsNumberTextViewTicketPaymentFragment)
            adultsNumberTextView.text = ticketsActivityViewModel.getAdultsNumberWhoWantsToBuy().toString()
        val minorsNumberTextView: TextView = view.findViewById(R.id.minorsNumberTextViewTicketPaymentFragment)
            minorsNumberTextView.text = ticketsActivityViewModel.getMinorsNumberWhoWantsToBuy().toString()
        val adultsPriceTextView: TextView = view.findViewById(R.id.adultsPriceTextViewTicketPaymentFragment)
            adultsPriceTextView.text = ticket.adults_price.toString()
        val minorsPriceTextView: TextView = view.findViewById(R.id.minorsPriceTextViewTicketPaymentFragment)
            minorsPriceTextView.text = ticket.minors_price.toString()
        val totalTextView: TextView = view.findViewById(R.id.totalPriceTextViewTicketPaymentFragment)
            totalTextView.text = ( (ticketsActivityViewModel.getAdultsNumberWhoWantsToBuy() * ticket.adults_price) + (ticketsActivityViewModel.getMinorsNumberWhoWantsToBuy() * ticket.minors_price) ).toString()

        val payButton: TextView = view.findViewById(R.id.paymentButtonTicketPaymentFragment)
        payButton.setOnClickListener(){
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val userTicketRequest = UserTicketRequest(-1,
                ticketsActivityViewModel.getIdUtente().toLong(),
                ticket.idticket,
                ticketsActivityViewModel.getAdultsNumberWhoWantsToBuy(),
                ticketsActivityViewModel.getMinorsNumberWhoWantsToBuy(),
                LocalDateTime.now().format(formatter).toString(),
                )
            this.context?.let { ctx -> ticketsPaymentFragmentViewModel.buyTicket(ctx, userTicketRequest) }
        }
    }


}