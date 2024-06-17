package com.example.railmanager.modules.ticketsModule.resultModule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.trainDbModule.TrainDetailsRequestMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel

class TicketsResultAdapter(private val data : ArrayList<Tickets>, ticketsActivityViewModel: TicketsActivityViewModel) : RecyclerView.Adapter<TicketsResultAdapter.ViewHolder>() {

    val trainDetailsRequestMethods = TrainDetailsRequestMethods()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val startLocationTextView = view.findViewById<TextView>(R.id.startLocationTextViewRvPaymentTicketsFragment)
        val endLocationTextView = view.findViewById<TextView>(R.id.endLocationTextViewRvPaymentTicketsFragment)
        val departureTimeTextView = view.findViewById<TextView>(R.id.startHourTextViewRvPaymentTicketsFragment)
        val arrivalTimeTextView = view.findViewById<TextView>(R.id.endHourTextViewRvPaymentTicketsFragment)
        val trainTypeTextView = view.findViewById<TextView>(R.id.trainTypeTextViewRvPaymentTicketsFragment)
        val trainClassNumberTextView = view.findViewById<TextView>(R.id.classNumberTextViewRvPaymentTicketsFragment)
        val adultsTicketPriceTextView = view.findViewById<TextView>(R.id.adultsPriceTextViewRvPaymentTicketsFragment)
        val minorsTicketPriceTextView = view.findViewById<TextView>(R.id.minorsPriceTextViewRvPaymentTicketsFragment)
        val availableSeatsTextView = view.findViewById<TextView>(R.id.availableSeatsTextViewRvPaymentTicketsFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_payment_tickets_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.departureTimeTextView.text = UsefulStaticMethods.formattaData(item.departure_time)
        holder.arrivalTimeTextView.text = UsefulStaticMethods.formattaData(item.arrival_time)
        holder.trainClassNumberTextView.text = "Numero classe: " + item.class_number.toString()
        trainDetailsRequestMethods.getTrainDetails(item.train_id) { trainDetails ->
            if (trainDetails != null) {
                holder.startLocationTextView.text = trainDetails.departure_city+","+trainDetails.departure_station
                holder.endLocationTextView.text = trainDetails.arrival_city+","+trainDetails.arrival_station
                holder.trainTypeTextView.text = trainDetails.train_type
                holder.availableSeatsTextView.text = "Posti disponibili: " + trainDetails.available_seats
            } else {
                // Gestisci il caso di errore o risposta nulla qui
                Log.d("TrainDetails", "Failed to retrieve train details")
            }
        }

        holder.adultsTicketPriceTextView.text = "Prezzo adulti: "+ item.adults_price.toString()
        holder.minorsTicketPriceTextView.text = "Prezzo minori: "+ item.minors_price.toString()

    }


}