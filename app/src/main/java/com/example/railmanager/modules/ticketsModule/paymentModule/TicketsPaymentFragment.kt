package com.example.railmanager.modules.ticketsModule.paymentModule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.trainDbModule.TrainDetailsRequest
import com.example.railmanager.modules.ticketsModule.resultModule.TicketsResultFragmentArgs
import com.google.gson.Gson


class TicketsPaymentFragment : Fragment() {

    private val args: TicketsPaymentFragmentArgs by navArgs()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticket = args.ticketToBuy

        // Recupera la stringa JSON
        val jsonTrainDetailsString = args.trainDetails

        // Converti da JSON a struttura dati complessa
        val gson = Gson()
        val trainDetails = gson.fromJson(jsonTrainDetailsString, TrainDetailsRequest::class.java)

        Log.d("Ho ricevuto questo: ", ticket.toString())
        Log.d("ANCHE QUESTO", trainDetails.toString())
    }


}