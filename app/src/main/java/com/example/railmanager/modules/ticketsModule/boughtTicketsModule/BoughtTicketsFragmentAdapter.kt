package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets

class BoughtTicketsFragmentAdapter(fragment: Fragment , val data : ArrayList<Tickets>) : RecyclerView.Adapter<BoughtTicketsFragmentAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val cardView = view.findViewById<View>(R.id.cardViewRvResultsTicketsFragment)
        val startLocationTextView = view.findViewById<TextView>(R.id.startLocationTextViewRvResultsTicketsFragment)
        val endLocationTextView = view.findViewById<TextView>(R.id.endLocationTextViewRvResultsTicketsFragment)
        val departureTimeTextView = view.findViewById<TextView>(R.id.startHourTextViewRvResultsTicketsFragment)
        val arrivalTimeTextView = view.findViewById<TextView>(R.id.endHourTextViewRvResultsTicketsFragment)
        val trainTypeTextView = view.findViewById<TextView>(R.id.trainTypeTextViewRvResultsTicketsFragment)
        val trainClassNumberTextView = view.findViewById<TextView>(R.id.classNumberTextViewRvResultsTicketsFragment)
        val adultsTicketPriceTextView = view.findViewById<TextView>(R.id.adultsPriceTextViewRvResultsTicketsFragment)
        val minorsTicketPriceTextView = view.findViewById<TextView>(R.id.minorsPriceTextViewRvResultsTicketsFragment)
        val availableSeatsTextView = view.findViewById<TextView>(R.id.availableSeatsTextViewRvResultsTicketsFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_results_tickets_fragment,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.departureTimeTextView.text = UsefulStaticMethods.formattaData(item.departure_time)
        holder.arrivalTimeTextView.text = UsefulStaticMethods.formattaData(item.arrival_time)
        holder.trainClassNumberTextView.text = "Numero classe: " + item.class_number.toString()
    }
}