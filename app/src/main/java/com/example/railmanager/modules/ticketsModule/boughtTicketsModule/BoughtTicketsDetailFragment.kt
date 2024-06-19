package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.ticketsModule.paymentModule.TicketsPaymentFragmentArgs


class BoughtTicketsDetailFragment : Fragment() {


    private val args: BoughtTicketsDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bought_tickets_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ticketDetail = args.boughtTicket
        val trainNameTextView =
            view.findViewById<TextView>(R.id.trainNameTextViewBoughtTicketsDetailFragment)
        trainNameTextView.text = ticketDetail.train_name
        val trainTypeTextView =
            view.findViewById<TextView>(R.id.trainTypeTextViewBoughtTicketsDetailFragment)
        trainTypeTextView.text = ticketDetail.train_type
        val trainStateTextView =
            view.findViewById<TextView>(R.id.trainStateTextViewBoughtTicketsDetailFragment)
        trainStateTextView.text = ticketDetail.state_description
        val ticketDepartureTextView =
            view.findViewById<TextView>(R.id.departureTextViewBoughtTicketsDetailFragment)
        ticketDepartureTextView.text = UsefulStaticMethods.formattaData(ticketDetail.departure_time)
        val ticketArrivalTextView =
            view.findViewById<TextView>(R.id.arrivalTextViewBoughtTicketsDetailFragment)
        ticketArrivalTextView.text = UsefulStaticMethods.formattaData(ticketDetail.arrival_time)
        val ticketDatePurchaseTextView =
            view.findViewById<TextView>(R.id.boughtDateTextViewBoughtTicketsDetailFragment)
        ticketDatePurchaseTextView.text = UsefulStaticMethods.formattaData(ticketDetail.date_purchase)
        val minorsNumberTextView =
            view.findViewById<TextView>(R.id.minorsNumberTextViewBoughtTicketsDetailFragment)
        minorsNumberTextView.text = ticketDetail.minors_number.toString()
        val adultsNumberTextView =
            view.findViewById<TextView>(R.id.adultsNumberTextViewBoughtTicketsDetailFragment)
        adultsNumberTextView.text = ticketDetail.adults_number.toString()
        val totalPriceTextView =
            view.findViewById<TextView>(R.id.totalPriceTextViewBoughtTicketsDetailFragment)
        totalPriceTextView.text = ( ticketDetail.minors_price * ticketDetail.minors_number + ticketDetail.adults_price * ticketDetail.adults_number).toString() + "€"

    }


}