package com.example.railmanager.modules.ticketsModule.resultModule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.railmanager.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.modules.ticketsModule.TicketsActivity
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel


class TicketsResultFragment : Fragment() {
    private val args: TicketsResultFragmentArgs by navArgs()
    val ticketsActivityViewModel : TicketsActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tickets_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ticketsList = args.ticketsList.toList()
        // Ora puoi utilizzare la lista di ticketsList come desideri
        for (ticket in ticketsList) {
            Log.d("ResultFragment", "ID: ${ticket.idticket}, Departure: ${ticket.departure_time}, Arrival: ${ticket.arrival_time}")
        }

        /*val originActivity = this.activity as TicketsActivity
        Log.d("Email utente" , originActivity.getUserEmail() )
        Log.d("Numero adulti e minori", originActivity.getAdultsAndMinorsNumber().toString())
          */
        val adapter = TicketsResultAdapter(ArrayList(ticketsList), ticketsActivityViewModel )

        val recyclerView : RecyclerView = view.findViewById(R.id.rvFragmentTicketsPayment)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }
}